package happy.core.scan;

import happy.core.AutoAppConfig;
import happy.core.member.Grade;
import happy.core.member.Member;
import happy.core.member.MemberService;
import happy.core.order.Order;
import happy.core.order.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

    @Test
    @DisplayName("빈 자동 등록 및 의존관계 잘 형성됐는지 확인")
    void aa(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        /*String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole()==BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName);
            }
        }*/
        AutoAppConfig autoAppConfig = ac.getBean("autoAppConfig", AutoAppConfig.class);
        System.out.println("autoAppConfig = " + autoAppConfig);
        
    }
    
    
    @Test
    void bb(){
        ApplicationContext ac= new AnnotationConfigApplicationContext(AutoAppConfig.class);
        OrderService orderService = ac.getBean(OrderService.class);
        MemberService memberService = ac.getBean(MemberService.class);
        Member member = new Member(1L , Grade.VIP , "lim");
        memberService.join(member);

        Order order = orderService.createOrder(1L, "VV", 20000);
        System.out.println(order.calculatePrice());

    }


}
