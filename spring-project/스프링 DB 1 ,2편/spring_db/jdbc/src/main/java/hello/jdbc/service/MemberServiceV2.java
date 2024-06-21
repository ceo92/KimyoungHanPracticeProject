package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동 , 풀을 고려한 종료
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV2 {

    private final MemberRepositoryV2 memberRepository;
    private final DataSource dataSource; //나중에 스프링에서 DI 해주게 설정할 것임
    //컨트롤러 => 서비스 => 리포지토리 순으로 의존하므로 의존관계 주입은 역순으로 ㅇㅇ 즉 각 컴포넌트의 객체를 역순으로 할당받아서 처리하니 최종적으로 컨트롤러에서 처리 후 뷰나 api로 응답 ㅇㅇ
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try{
            con.setAutoCommit(false); //트랜잭션 시작
            //비즈니스 로직 수행
            bizLogic(con, toId, money, fromId);
            con.commit(); //정상 수행 시 커밋

        }catch (Exception e){
            con.rollback(); //validation으로 검증한 부분을 여기서 잡네
            throw new IllegalStateException(e);
        }finally {
            release(con);
        }




    }

    private void bizLogic(Connection con, String toId, int money, String fromId) throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId); //출금되는 회원 객체 반환
        Member toMember = memberRepository.findById(con, toId); //입금받을 회원 객체 반환

        memberRepository.updateMember(con, fromId, fromMember.getMoney() - money); //출금되는 회원 객체에서 money만큼 뺌
        validation(con, toMember); //첫번째 감소로직에서 문제 생기면 두번째로 못넘어가짐
        memberRepository.updateMember(con, toId, toMember.getMoney() + money); //입금되는 회원 객체에서 money만큼 더함
    }

    private static void release(Connection con) {
        if (con != null){
            try{
                con.setAutoCommit(true);
                con.close();
                //그냥 con.close() 하면 커넥션풀로 돌아감 근데 그냥 close() 하면 setAutocommit이 false된 상태로 돌아가니 디폴트값인 true 로 바꿔주고 돌아가게해야됨

            }catch (Exception e){
                log.info("error" , e);
            }
        }
    }

    private static void validation(Connection con , Member toMember) throws SQLException {
        if (toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
