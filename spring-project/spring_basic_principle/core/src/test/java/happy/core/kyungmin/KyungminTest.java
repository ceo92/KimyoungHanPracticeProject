package happy.core.kyungmin;

import happy.core.discount.DiscountPolicy;
import happy.core.discount.FixDiscountPolicy;
import happy.core.discount.RateDiscountPolicy;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

public class KyungminTest {




    @Test
    @DisplayName("자동 빈 등록 복습")
    void review(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(KyungminConfig.class , KyungminController.class , KyungminService.class);
        //Singleton : Config 생성 -> Service 생성 -> Controller -> 기존 객체 가져옴 -> 로직 수행 1  -> 기존 객체 가져옴 -> 로직 수행 2 -> 컨트롤러 x -> 서비스 x -> Config x
        //Prototype : Config 생성 -> Service 생성(스프링이 DI 위하여 호출) -> Controller 생성 -> Service는 주입 후 종료 -> 1 -> 2 -> 컨트롤러 소멸 -> Config 소멸
        //Provider<Prototype> : Config 생성 -> Controller 생성(Provider 객체가 대신 주입) -> Service 생성(Provider은 객체 DL 위한 코드 있을 때 생성) and 종료-> 1 -> Service 생성 and 종료 ->1-> Controller 소멸 -> Config 소멸
        // 프로토타입도 싱글톤의 일부이니 일단 싱글톤과 실행 알고리즘 및 로직 같음 근데 종료만 안될뿐이지 ㅇㅇ
        KyungminController kyungminController1= ac.getBean(KyungminController.class);
        kyungminController1.logic();

        KyungminController kyungminController2 = ac.getBean(KyungminController.class);
        kyungminController1.logic();


        ac.close();
    }
    //내가 한 이 예제는 그냥 프로토타입이 싱글톤에 종속된 예제가 아닌 그냥 프로토타입 빈 여러 개 생성하면 어떻게 되는지에 대한 예제임
    // 즉 프로토타입이 싱글톤에 종속된 예제를 보여주려면 내가 김영한 쌤이랑 한 예제로 해야됨


    static class KyungminConfig{
        @PostConstruct
        public void init(){
            System.out.println("Config → init");
        }
        @PreDestroy
        public void close(){
            System.out.println("Config → close");
        }
    }
    @Scope("singleton") //클라이언트
    @Component
    static class KyungminController{
        private final ObjectProvider<KyungminService> serviceObjectProvider; //컨트롤러는 싱글톤이니 프로토타입이 저장되는 뭐가 저장되든 알바없음

        @Autowired
        public KyungminController(ObjectProvider<KyungminService> serviceObjectProvider){
            this.serviceObjectProvider = serviceObjectProvider;
        }


        public void logic(){
            KyungminService kyungminService = serviceObjectProvider.getObject();
            kyungminService.addValue();
            Long value = kyungminService.getValue();
            System.out.println("value = " + value);
        }

        @PostConstruct
        public void init(){
            System.out.println("Controller → init");
        }

        @PreDestroy
        public void close(){
            System.out.println("Controller → close");
        }

    }

    @Scope("prototype") //서버(서비스)
    @Component
    static class KyungminService{

        public void addValue(){
            value++;
        }

        public Long getValue() {
            return value;
        }

        private Long value = 0L;


        @PostConstruct
        public void init(){
            System.out.println("Service → init");

        }

        @PreDestroy
        public void close(){
            System.out.println("Service → close");
        }

    }

}
