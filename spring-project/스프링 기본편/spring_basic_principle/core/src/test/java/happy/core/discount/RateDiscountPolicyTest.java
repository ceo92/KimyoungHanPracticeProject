package happy.core.discount;

import happy.core.member.Grade;
import happy.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {
    DiscountPolicy discountPolicy = new RateDiscountPolicy();
    @Test
    void discount() {
        Member member = new Member(1L , Grade.VIP , "kim");
        int discountPrice = discountPolicy.discount(member, 10000);
        assertThat(discountPrice).isEqualTo(1000);


    }
}