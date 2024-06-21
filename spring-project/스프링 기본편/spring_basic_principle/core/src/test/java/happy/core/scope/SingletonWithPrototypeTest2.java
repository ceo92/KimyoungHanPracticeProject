package happy.core.scope;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class SingletonWithPrototypeTest2 {



    //Config 클래스 없이 바로 클라이언트 코드에서 작업했음
    @Test
    void singletonClientUsePrototype(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(ConfigBeanClass.class); //이미 여기서 빈이 생성
        //이  친구는 1 객체를 주입받아서 평생 사용하는 것임 그래서 계속 1이 나올 수밖에 없는 거고 ㅇㅇ 프로토타입 빈은 맨처음에만 생성되었으니!
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class); //이건 그냥 새로운 프로토타입 객체에서 0 -> 1이 되고
        int count1 = prototypeBean1.getCount();
        System.out.println("count1 = " + count1);


        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class); // 이것도 그냥 새로운 프로토타입 객체에서 0->1이 됨
        int count2 = prototypeBean2.getCount();
        System.out.println("count2 = " + count2);

        //클라이언트 싱글톤 빈 객체 생성 이 친구는 싱글톤 빈이니 아까와 같은 객체의 주소가 bean2에 반환됨,및 의존관계 주입도 아주 먼 옛날에 끝났음
        //즉 모든 게 기존 거를 똑같이 사용하는 것
        ac.close();
        //PrototypeBean 생성(의존관계 주입 후 종료) -> ClientBean 생성 -> PrototypeBean 생성(getBean해서 반환하고 종료) -> 테스트 -> PrototypeBean 생성(getBean해서 반환하고 종료) -> 테스트 -> ClientBean 종료

    }
    @Configuration
    @ComponentScan
    static class ConfigBeanClass{

    }


    @Scope("singleton")
    @Component
    static class ClientBean{
        private PrototypeBean prototypeBeanObjectProvider;

        @Autowired
        public ClientBean(PrototypeBean prototypeBeanObjectProvider) {
            this.prototypeBeanObjectProvider = prototypeBeanObjectProvider;
        }

        public int logic(){

            //prototypeBeanObjectProvider.addCount();
            int count = prototypeBeanObjectProvider.getCount();
            return count;
        } //1증가한 값 반환

        @PostConstruct
        public void init(){
            System.out.println("ClientBean → init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("ClientBean → destroy");
        }
    }



    @Scope("prototype")
    @Component
    static class PrototypeBean{

        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            addCount();
            System.out.println("PrototypeBean → init");
        }


        @PreDestroy //ObjectProvider 쓰면 걍 지역변수라 내부적으로 종료니 이건 안실행되지않나?
        public void close(){
            System.out.println("PrototypeBean → close");
        }

    }
}
