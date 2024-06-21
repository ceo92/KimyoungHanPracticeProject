package happy.core.singleton;

import happy.core.AppConfig;
import happy.core.member.MemberService;
import happy.core.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void aaa(){
        AppConfig appConfig = new AppConfig();
        OrderService orderService1 = appConfig.orderService();
        OrderService orderService2 = appConfig.orderService();

       /* assertThat(orderService1).isEqualTo(orderService2);
        org.junit.jupiter.api.Assertions.assertThrows(AssertionFailedError.class , ()->or)*/

    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();
        System.out.println("instance1 = " + instance1);
        System.out.println("instance2 = " + instance2);

        Assertions.assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("스프링 컨테이너의 싱글톤 컨테이너 기능 이용")
    void springContainer(){

        //스프링 컨테이너의 싱글톤 이용
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        //Assertions.assertThat(memberService1).isSameAs(memberService2);

        //싱글톤 없이 그냥 자바 코드 실행할 경우 객체의 비교
        AppConfig aa = new AppConfig();
        MemberService memberService3 = aa.memberService();
        MemberService memberService4 = aa.memberService();
        //Assertions.assertThat(memberService3).isSameAs(memberService4);

        //자바 코드로 싱글톤 생성 후 객체의 비교
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();
        Assertions.assertThat(instance1).isSameAs(instance2);



    }

    @Test
    @DisplayName("오류 관찰")
    void acac(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(StatefulServiceTestClass.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        int k1 = statefulService1.order("k1", 10000);
        int k2 = statefulService2.order("k2", 20000);

        System.out.println(k2);
    }
    static class StatefulServiceTestClass {
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }

    }

}
