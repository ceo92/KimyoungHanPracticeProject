package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import hello.jdbc.repository.MemberRepositoryV4_1;
import hello.jdbc.repository.MemberRepositoryV4_2;
import hello.jdbc.repository.MemberRepositoryV5;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 트랜잭션 - DataSource , transactionManager 자동 등록
 */
@Slf4j
@SpringBootTest
class MemberServiceV4Test {

    @Autowired
    private MemberServiceV4 memberService;
    @Autowired
    private MemberRepository memberRepository;


    @TestConfiguration //테스트용 설정정보 클래스 , 내부 클래스 걍 만들었다 생각
    static class TestConfig{
        private final DataSource dataSource;
        //자동 빈 등록 구현
        // 어차피 스프링이 기본으로 등록된 히카리풀 DataSource를 자동으로 DI 해주니 ㅇㅇ
        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }
        @Bean
        public SQLExceptionTranslator sqlExceptionTranslator(){
            return new SQLErrorCodeSQLExceptionTranslator(dataSource);
        }


        @Bean
        public MemberRepository memberRepository(){
            return new MemberRepositoryV5(dataSource);
        }
        @Bean
        public MemberServiceV4 memberServiceV4(){
            return new MemberServiceV4(memberRepository());
        }

    }

    @Test
    void appCheck(){
        log.info("memberService class={}", memberService.getClass());
        log.info("memberRepository class={}", memberRepository.getClass());
        Assertions.assertThat(AopUtils.isAopProxy(memberService)).isTrue();
        Assertions.assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();
    }


    @AfterEach
    void after() {
        memberRepository.deleteMember("memberA");
        memberRepository.deleteMember("memberB");
        memberRepository.deleteMember("ex");
    }


    @Test
    @DisplayName("정상 이체")
    void accountTransfer()  {
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
    void accountTransferEx() {
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