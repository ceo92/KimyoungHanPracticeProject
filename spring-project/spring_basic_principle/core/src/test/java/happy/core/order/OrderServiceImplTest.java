package happy.core.order;

import happy.core.AppConfig;
import happy.core.member.Grade;
import happy.core.member.Member;
import happy.core.member.MemberService;
import happy.core.member.MemberServiceImpl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrderServiceImplTest {
    MemberService memberService;
    OrderService orderService;
    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }// 각 테스트 독립되게 수행하게끔 객체 테스트 끝날 때마다 새로 생성

    @Test
    void createOrder(){
       //when
        Member member = new Member(10L , Grade.VIP , "영희");
        memberService.join(member);
        //then
        Order order = orderService.createOrder(member.getId(), "콜라", 10000);
        Assertions.assertThat(order.calculatePrice()).isEqualTo(9000);
        //결과
    }
}