package hello.jdbc.exception.translator;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
import hello.jdbc.repository.ex.MyDuplicateKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ExTranslatorV1Test {
    Repository repository;
    Service service;
    @BeforeEach
    void init(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        repository = new Repository(dataSource);
        service = new Service(repository);
    }

    @Test
    void duplicateKeySave(){
        service.create("myId");
        service.create("myId");
    }
    @RequiredArgsConstructor
    static class Service{
        private final Repository repository;

        public void create(String memberId) {
            try {
                repository.save(new Member(memberId, 0));
            }catch (MyDuplicateKeyException e){
                //이 오류는 서비스에서 처리해야되니 여기서 로그 찍네
                log.info("키 중복 , 복구 시도 ");
                String retryId = generateNewId(memberId);
                log.info("retryId={}", retryId);
                repository.save(new Member(retryId, 0));
            }catch (MyDbException e){
                //이건 굳이 안잡아도 어차피 던져지는데 그냥 여러개 캐치할수있다는 점 보여주기 위하여 1
                log.info("데이터 접근 계층 예외" , e);
                throw e;
            }
        }
        private String generateNewId(String memberId){
            return memberId + new Random().nextInt(10000);
        }
    }
    @RequiredArgsConstructor
    static class Repository{

        private final DataSource dataSource;

        public Member save(Member member){
            String sql = "insert into member(member_id , money) values(? , ?)";
            Connection con = null;
            PreparedStatement pstmt = null;
            try{
                con = dataSource.getConnection();
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1,member.getMemberId());
                pstmt.setInt(2,member.getMoney());
                pstmt.executeUpdate();
                return member;

            }catch (SQLException e){
                if (e.getErrorCode() ==23505){
                    throw new MyDuplicateKeyException(e); //난 서비스에서 예외 처리하고싶어 => 그럴려면 db에서 에러 코드를 반환받아서 나만의 런타임에러로 변경하려해 => 서비스에서 해당 예외가 오면 예외를 처리하게끔 할거야

                }
                throw new MyDbException(e);
            }finally {
                JdbcUtils.closeStatement(pstmt);
                JdbcUtils.closeConnection(con);
            }
        }

    }

}
