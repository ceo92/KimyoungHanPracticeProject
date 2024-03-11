package happy.core;


import happy.core.discount.DiscountPolicy;
import happy.core.discount.FixDiscountPolicy;
import happy.core.discount.RateDiscountPolicy;
import happy.core.member.MemberRepository;
import happy.core.member.MemberService;
import happy.core.member.MemberServiceImpl;
import happy.core.member.MemoryMemberRepository;
import happy.core.order.OrderService;
import happy.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
public class AppConfig {
    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }
    //할인 => 리포지토리 => 오더 => 멤버서비스
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository() , discountPolicy());

    }

    @Bean
    public MemberRepository memberRepository(){
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService(){
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
}
