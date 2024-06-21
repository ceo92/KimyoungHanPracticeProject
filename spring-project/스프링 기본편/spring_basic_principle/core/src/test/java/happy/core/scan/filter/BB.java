package happy.core.scan.filter;

import happy.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class BB {
    @Test
    void testBean(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean{

        @Autowired(required = false) //자동 주입 안 되게
        public void setNoBean1(Member member){
            System.out.println("member = " + member);
        }

        
        public void setNoBean2(@Nullable Member member){
            System.out.println("member = " + member);
        }

        
        public void setNoBean3(Optional<Member> member){
            System.out.println("member = " + member);
        }
    }
}


