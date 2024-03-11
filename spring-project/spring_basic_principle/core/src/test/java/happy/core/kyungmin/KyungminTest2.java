package happy.core.kyungmin;

import happy.core.AutoAppConfig;
import happy.core.discount.DiscountPolicy;
import happy.core.member.Grade;
import happy.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

public class KyungminTest2 {


    @Test
    @DisplayName("같은 빈 두 개 이상일 때 전부 조회하는법")
    void findSameTypeOfAllBeans(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class , AllBeansAreHere.class);
        AllBeansAreHere allBeansAreHere = ac.getBean(AllBeansAreHere.class);
        Member member = new Member(1L , Grade.VIP , "christmas");
        int discount = allBeansAreHere.discount(member, 90000, "rateDiscountPolicy");
        System.out.println("discount = " + discount);
        allBeansAreHere.show();

    }


    static class AllBeansAreHere{
        private final Map<String , DiscountPolicy> discountPolicyMap;
        private final List<DiscountPolicy> discountPolicyList;

        public AllBeansAreHere(Map<String , DiscountPolicy> discountPolicyMap ,  List<DiscountPolicy> discountPolicyList){
            this.discountPolicyMap = discountPolicyMap;
            this.discountPolicyList = discountPolicyList;
        }

        public int discount(Member member , int price , String discountPolicy){

            DiscountPolicy real = discountPolicyMap.get(discountPolicy);
            int discount = real.discount(member, price);
            return discount;
        }

        public void show(){
            for (String s : discountPolicyMap.keySet()) {
                System.out.println("s = " + s);
            }
        }

    }



}
