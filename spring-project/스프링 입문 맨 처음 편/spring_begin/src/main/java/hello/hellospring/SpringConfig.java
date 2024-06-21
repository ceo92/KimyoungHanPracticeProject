package hello.hellospring;


import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    //private DataSource dataSource;
    //private EntityManager entityManager;
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean //빈을 등록할거야
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }
    /*@Bean
    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(entityManager);

    }*/


    //스프링이 뜰 때 Configuration읽고 @Bean을 스프링 빈에 등록하란 뜻이네 라고 인식함
    //그리고  @링Bean에 포함된 로직을 스프 빈에 등록함
    // 자동이 아닌 수동적으로 가능 !
    //DI로 리포지토리 객체를 삽입해줬네 ㅇㅇ
}

