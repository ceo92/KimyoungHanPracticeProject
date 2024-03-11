package happy.core;

import happy.core.discount.DiscountPolicy;
import happy.core.discount.FixDiscountPolicy;
import happy.core.discount.RateDiscountPolicy;
import happy.core.member.MemberRepository;
import happy.core.member.MemberService;
import happy.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Map;

public class ApplicationContextSameTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameConfig.class);
    @Test
    @DisplayName("중복된 타입 하나만 가져오기")
    void aaa(){
        DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
        System.out.println("bean = " + bean.getClass());
        //Assertions.assertThrows(NoUniqueBeanDefinitionException.class , ()->ac.getBean(DiscountPolicy.class));
    }


    @Test
    @DisplayName("중복된 타입 전부 가져오기")
    void bbb(){
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + "value = "+beansOfType.get(key));
        }

        Assertions.assertThat(beansOfType.size()).isEqualTo(2);
        //Assertions.assertThrows(NoUniqueBeanDefinitionException.class , ()->ac.getBean(DiscountPolicy.class));
    }

    @Configuration
    static class SameConfig{

        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }

        @Primary
        @Bean 
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }
    }
}
