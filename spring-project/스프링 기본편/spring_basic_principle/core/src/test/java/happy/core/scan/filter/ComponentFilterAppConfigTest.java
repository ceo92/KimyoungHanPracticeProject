package happy.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {

    @Test
    void aa(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigTestClass.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        //BeanB beanB = ac.getBean("beanB", BeanB.class);

        Assertions.assertThat(beanA).isNotNull();
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,() -> ac.getBean("beanB" , BeanB.class));
    }


    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION , classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION , classes = MyExcludeComponent.class)

    ) //이렇게 사용자 지정 컴포넌트 스캔할 수 있는 클래스 완성! , 당연히 아무것도 있으면 안 됨
    static class AppConfigTestClass{

    }
}
