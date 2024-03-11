package happy.core;

import happy.core.member.MemberService;
import happy.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextBasicFindTest {


    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class);
        // 타입만 지정해서getBean 가능 근데 같은 타입 여러 개면 곤란해짐 ㅇㅇ
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    
    @Test
    @DisplayName("실패 테스트")
    void error(){


        org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class , ()->
                ac.getBean("xxxx", MemberService.class) );
        //오른쪽 문장을 실행했을 때 지정한 예외 클래스가 터지면 테스트 성공 아니면 실패
    }
}
