package happy.core.scope;

import happy.core.member.MemberRepository;
import happy.core.member.MemoryMemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;

public class SingletonTest {

    @Test
    void singletonBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);


        Assertions.assertThat(singletonBean1).isSameAs(singletonBean2);
        ac.close();


    }

    @Scope("singleton")
    static class SingletonBean {

        @PostConstruct
        public void init(){
            System.out.println("SingletonScopeBeanConfig.init");
        }

        @PreDestroy
        public void close(){
            System.out.println("SingletonScopeBeanConfig.close");
        }

        @Bean
        public MemberRepository memberRepository(){
            return new MemoryMemberRepository();
        }
    }
}
