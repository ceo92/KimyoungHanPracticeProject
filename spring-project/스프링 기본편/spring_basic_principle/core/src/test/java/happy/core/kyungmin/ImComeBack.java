package happy.core.kyungmin;

import happy.core.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

public class ImComeBack {

    @Test
    void test() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @ComponentScan
    static class ImConfig{

    }

    @Component
    static class BeanP{

    }

    @Component
    static class BeanQ{

    }

    @Component
    static class BeanR{

    }



}
