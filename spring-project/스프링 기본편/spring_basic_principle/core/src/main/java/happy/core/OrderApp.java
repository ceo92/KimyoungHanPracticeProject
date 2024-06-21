package happy.core;

import happy.core.member.Grade;
import happy.core.member.Member;
import happy.core.member.MemberService;
import happy.core.member.MemberServiceImpl;
import happy.core.order.Order;
import happy.core.order.OrderService;
import happy.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
        Long memberId = 1L;
        Member member = new Member(memberId , Grade.VIP , "kim"); //회원 정보를 이렇게 저장한 거고
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);


        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        memberService.join(member);

        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        Order order = orderService.createOrder(memberId, "Morphine", 20000);

        //결과
        System.out.println(order.calculatePrice());

    }
}
