package happy.core.autowired;

import happy.core.AutoAppConfig;
import happy.core.discount.DiscountPolicy;
import happy.core.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    @DisplayName("같은 타입의 여러 빈 모두 조회")
    void findAllBean(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class , DiscountService.class);
        //DiscountService만 설정 정보이자 빈 등록을 했음 근데 @Bean도 없고 @
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L , Grade.VIP , "userA");
        int discountPrice = discountService.discount(member , 20000 , "rateDiscountPolicy");
        Assertions.assertThat(discountPrice).isEqualTo(2000);


    }




    static class DiscountService{
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
        }

        public int discount(Member member, int i, String fixDiscountPolicy) {

            DiscountPolicy discountPolicy  = policyMap.get(fixDiscountPolicy);
            int discount = discountPolicy.discount(member, i);
            return discount;


            /*if (member.getGrade().compareTo(Grade.VIP)==0){
                if (fixDiscountPolicy.equals("fixDiscountPolicy")){
                    return i-=1000;
                }
                else if(fixDiscountPolicy.equals("rateDiscountPolicy")){
                    return i=i*(9/10);
                }
            }
            return 0;*/
        }
    }
}
