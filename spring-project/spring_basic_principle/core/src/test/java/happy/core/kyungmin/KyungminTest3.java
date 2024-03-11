package happy.core.kyungmin;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import net.bytebuddy.implementation.bind.annotation.FieldProxy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

public class KyungminTest3 {


    @Test
    void test(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class , PrototypeBean.class);
        SingletonBean singletonBean = ac.getBean(SingletonBean.class);
        Long value1 = singletonBean.logic();
        Assertions.assertThat(value1).isEqualTo(1);

        Long value2 = singletonBean.logic();
        Assertions.assertThat(value2).isEqualTo(2);


        //프로토타입 특징은 필요할때 생성이니 ㅇㅋㅇㅋ
        ac.close();
    }


    @Test
    void test2(){

    }


    @Scope("singleton")
    static class SingletonBean{
        private final PrototypeBean prototypeBean;

        @Autowired
        public SingletonBean(PrototypeBean prototypeBean){
            this.prototypeBean = prototypeBean;
        }

        @PostConstruct
        public void init(){
            System.out.println("SingletonBean.init"+this);
        }

        public Long logic(){
            Long value = prototypeBean.getValue();
            return value;
        }

        @PreDestroy
        public void destroy(){
            System.out.println("SingletonBean.destroy"+this);
        }
    }

    @Scope(value = "prototype" )
    static class PrototypeBean{
        private Long value = 0L;
        public void addValue(){
            value++;
        }
        public Long getValue(){
            addValue();
            return value;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init"+this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy"+this);
        }
    }
}
