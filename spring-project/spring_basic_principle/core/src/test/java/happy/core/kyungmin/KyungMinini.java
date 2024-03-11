package happy.core.kyungmin;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class KyungMinini {

    @Test
    @DisplayName("AnnotationConfigApplicationContext 시험")
    void aa(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(Bean1.class , Bean2.class , Config.class );

    }

    @Component
    static class Config{
        @PostConstruct
        public void init(){
            System.out.println("Config.init");
        }
    }
    @Component
    static class Bean1{
        @PostConstruct
        public void init(){
            System.out.println("Bean1.init");
        }
    }
    @Component
    static class Bean2{
        @PostConstruct
        public void init(){
            System.out.println("Bean2.init");
        }
    }
}


