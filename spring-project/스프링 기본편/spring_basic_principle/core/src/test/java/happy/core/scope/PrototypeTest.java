package happy.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test
    void prototypeTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("hello");
        PrototypeBean pb1 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeTest1 = " + pb1);

        PrototypeBean pb2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeTest2 = " + pb2);

        ac.close();
        Assertions.assertThat(pb1).isNotSameAs(pb2);

    }

    @Scope("prototype")
    static class PrototypeBean{

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void close(){
            System.out.println("PrototypeBean.close");
        }
    }
}
