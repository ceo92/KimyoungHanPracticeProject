package happy.core.discount;

import happy.core.member.Member;
import happy.core.order.Order;

public interface DiscountPolicy {
    /**
     *
     * @return 할인 대상 금액을 리턴해줌 1000원 할인 시 1000원 리턴
     */
    int discount(Member member , int price);
}
