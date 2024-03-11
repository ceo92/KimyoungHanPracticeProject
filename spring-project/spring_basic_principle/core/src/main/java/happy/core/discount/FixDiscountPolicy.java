package happy.core.discount;

import happy.core.member.Grade;
import happy.core.member.Member;
import happy.core.order.Order;
import org.springframework.stereotype.Component;


@Component
public class FixDiscountPolicy implements DiscountPolicy{
    private final int discountFixAmount = 1000;


    //굳이 price 인수를 받나?? 어차피 고객의 등급에 따라 정해지는 할인 금액값을 리턴해줄 텐데 ㅎㅎ;;
    @Override
    public int discount(Member member, int price) {
        return member.getGrade().compareTo(Grade.VIP)==0?discountFixAmount:0;

    }
}
