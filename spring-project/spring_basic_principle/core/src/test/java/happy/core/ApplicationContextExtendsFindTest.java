package happy.core;

import happy.core.discount.DiscountPolicy;
import happy.core.discount.FixDiscountPolicy;
import happy.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
    
    @Test
    @DisplayName("부모 타입으로 조회 시 자식이 둘 이상 있으면 중복 오류가 발생")
    public void aaa(){
        //DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
    }
    @Test
    @DisplayName("부모 타입으로 조회 시 자식이 둘 이상 있으면 빈 이름(키)을 지정하면 된다 . 특정지어지기 때문")
    public void bbb(){
        DiscountPolicy bean = ac.getBean("fixDiscountPolicy",DiscountPolicy.class); //추상화 변수 및 추상화를 반환하는게 다형성 측면에서 좋음
        assertThat(bean).isInstanceOf(DiscountPolicy.class);

    }
    @Test
    @DisplayName("부모 타입으로 조회 시 자식이 둘 이상 있으면 지정된 타입으로 가져올 수 있다?")
    public void ccc(){
        Map<String , DiscountPolicy> mapBeans = ac.getBeansOfType(DiscountPolicy.class); //추상화 변수 및 추상화를 반환하는게 다형성 측면에서 좋음
        for (String key : mapBeans.keySet()) {
            System.out.println("key = " + key + "value = "+mapBeans.get(key));
        }
        //assertThat(bean).isInstanceOf(DiscountPolicy.class);
    }
    @Test
    @DisplayName("부모 타입으로 조회 시 Object타입으로 모두 조회")
    public void ddd(){
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + "value = "+beansOfType.get(key));
        }


        //assertThat(bean).isInstanceOf(DiscountPolicy.class);
    }

    @Configuration
    static class TestConfig{
        
        
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }
}
