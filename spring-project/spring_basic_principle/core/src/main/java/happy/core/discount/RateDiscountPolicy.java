package happy.core.discount;

import happy.core.annotation.MainDiscountPolicy;
import happy.core.member.Grade;
import happy.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{
    private final int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        //아 이때를 위해서 price 받았구나 ㅎㅎ
        if (member.getGrade().compareTo(Grade.VIP)==0){
            return price*discountPercent/100;
        }else{
            return 0;
        }
    }
}
