package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
class MemberRepositoryV41Test {
    MemberRepositoryV4_1 memberRepository;
    HikariDataSource dataSource;

    //커넥션 풀링 , 설정부
    @BeforeEach
    public void beforeEach(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        memberRepository = new MemberRepositoryV4_1(dataSource);
    }


    @Test
    void crud() throws SQLException {
        //save
       Member member = new Member("member30" , 10000);
       memberRepository.save(member);


        //findById
        Member findMember = memberRepository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        Assertions.assertThat(findMember).isEqualTo(member);

        //update
        memberRepository.updateMember(member.getMemberId() , 30000);
        Member updatedMember = memberRepository.findById(member.getMemberId());
        Assertions.assertThat(updatedMember.getMoney()).isEqualTo(30000);
        memberRepository.deleteMember(member.getMemberId());
        //delete
        Assertions.assertThatThrownBy(() -> memberRepository.findById(member.getMemberId())).isInstanceOf(NoSuchElementException.class);

    }
}