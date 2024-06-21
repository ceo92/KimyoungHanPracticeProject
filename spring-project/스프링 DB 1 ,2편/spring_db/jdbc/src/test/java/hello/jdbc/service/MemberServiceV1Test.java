package hello.jdbc.service;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 기본 동작 , 트랜잭션이 없어서 문제 발생
 */
class MemberServiceV1Test {

    MemberServiceV1 memberService;
    MemberRepositoryV1 memberRepository;
    HikariDataSource dataSource;

    //설정 정보 클래스 , 스프링으로 치면 해당 @Configuration 클래스가 각각의 @Bean 들을 빈으로 등록해서 DI 해주는 것!
    @BeforeEach
    public void beforeEach() {
        //의존관계 : 컨트롤러 => 서비스 => 리포지토리 => dataSource 방향으로 의존하게 됨
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV1(memberRepository);
    }



    @AfterEach
    void after() throws SQLException{
        memberRepository.deleteMember("memberA");
        memberRepository.deleteMember("memberB");
        memberRepository.deleteMember("ex");
    }


    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        Member member1 = new Member("memberA", 10000);
        Member member2 = new Member("memberB", 10000);
        memberRepository.save(member1);
        memberRepository.save(member2);

        int money = 5000;
        memberService.accountTransfer(member1.getMemberId() , member2.getMemberId() , money);

        Member findMember1 = memberRepository.findById(member1.getMemberId());
        Member findMember2 = memberRepository.findById(member2.getMemberId());

        Assertions.assertThat(findMember1.getMoney()).isEqualTo(5000);
        Assertions.assertThat(findMember2.getMoney()).isEqualTo(15000);


    }


    @Test
    @DisplayName("이체 중 예외 발생")
    void accountTransferEx() throws SQLException {
        Member member1 = new Member("memberA", 10000);
        Member member2 = new Member("ex", 10000);
        memberRepository.save(member1);
        memberRepository.save(member2);

        int money = 5000;

        Assertions.assertThatThrownBy(() -> memberService.accountTransfer(member1.getMemberId(), member2.getMemberId(), money)).isInstanceOf(IllegalStateException.class);


        Member findMember1 = memberRepository.findById(member1.getMemberId());
        Member findMember2 = memberRepository.findById(member2.getMemberId());

        Assertions.assertThat(findMember1.getMoney()).isEqualTo(10000);
        Assertions.assertThat(findMember2.getMoney()).isEqualTo(10000);


    }
}