package happy.core;

import happy.core.member.*;
import happy.core.order.Order;
import happy.core.order.OrderService;
import happy.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {

        //객체 생성
        Member member = new Member(1L , Grade.VIP , "이경민"); // 도메인 객체 하나 생성 즉 회원정보 하나 생성한 것임
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        //서비스 테스트 로직
        /*MemberService memberService;
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();*/
        memberService.join(member);
        //결과
        System.out.println(memberService.findMember(member.getId()).equals(member));

        //리포지토리 테스트 로직
        MemberRepository memberRepository  = new MemoryMemberRepository();
        memberRepository.save(member);

        //결과
        System.out.println(member.equals(memberRepository.findById(member.getId())));






    }

}
