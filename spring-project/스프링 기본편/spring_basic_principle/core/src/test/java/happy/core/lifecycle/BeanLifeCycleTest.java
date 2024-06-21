package happy.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {


    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        //위에 코드는 Config클래스로  LifeCycleConfig를 지정하면서 빈으로 등록했음 그 다음으로 NetworkClient 빈 등록 작업 일어남
        // 빈 등록 ㄷ
        NetworkClient client = ac.getBean(NetworkClient.class);
        client.setUrl("http://naver.com");
        ac.close();

    }



    @Configuration
    static class LifeCycleConfig{
        @Bean//(initMethod = "init" , destroyMethod = "close")
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient(); // 빈 등록할 객체가 우선 생성됨 ㅇㅇ
            networkClient.setUrl("http://daum.com");
            return networkClient; //url을 naver로 담은 객체를 빈으로 등록한다.
            //아마 이 예제는 설정자 주입 시 처음에 값이 초기화가 안된다라는 걸 보ㅕㅇ주는 예일걸?
            // 빈 등록과 의존관계 주입은
        }
    }

}
