package hello.jdbc.service;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import hello.jdbc.repository.MemberRepositoryV3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

/**
 * 트랜잭션 - 커넥션 파라미터 전달 방식 동기화
 */
class MemberServiceV3_1Test {

    MemberServiceV3_1 memberService;
    MemberRepositoryV3 memberRepository;


    //설정 정보 클래스 , 스프링으로 치면 해당 @Configuration 클래스가 각각의 @Bean 들을 빈으로 등록해서 DI 해주는 것!
    @BeforeEach
    public void beforeEach() {
        //의존관계 : 컨트롤러 => 서비스 => 리포지토리 => dataSource 방향으로 의존하게 됨
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource); //JDBC 관련 구현체

        memberRepository = new MemberRepositoryV3(dataSource);
        memberService = new MemberServiceV3_1(memberRepository, transactionManager);
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