import happy.core.AppConfig;
import happy.core.member.MemberRepository;
import happy.core.member.MemberService;
import happy.core.member.MemberServiceImpl;
import happy.core.order.OrderService;
import happy.core.order.OrderServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

public class ApplicationContextInfoTest {



    @Test
    @DisplayName("KyungMin")
    void asdasd(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("ac = " + ac);
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        System.out.println("memberService = " + memberService.getClass());

        

    }

    @Test
    @DisplayName("Print All Beans")
    void findAllBean(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        String[] names = ac.getBeanDefinitionNames(); //빈 이름 가져오기

        for (String name : names) {
            Object bean = ac.getBean(name);
            System.out.println("name = " + name+ " , object = "+bean);
        }
    }


    @Test
    @DisplayName("Print Application Beans")
    void findApplicationBean(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        String[] names = ac.getBeanDefinitionNames(); //빈 이름 가져오기

        for (String name : names) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(name);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(name);
                System.out.println("name = " + name+ " , object = "+bean);
            }
        }
    }










}




