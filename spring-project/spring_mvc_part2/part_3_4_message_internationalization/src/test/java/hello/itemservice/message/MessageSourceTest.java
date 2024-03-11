package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest //스프링 부트가 자동으로 메시지 소스 등록(application.properties에 등록된놈들 전부 할당하니) , 그래서 자바 코드로 테스트 하는게 아닌 스프링 부트의 ㅎ미으
public class MessageSourceTest {
    @Autowired
    MessageSource ms; //기본으로 등록함 messagesource 스프링부트 덕에 ㅇㅇ
    @Test
    void helloMessage1(){
        String result = ms.getMessage("hello", null, null);
        Assertions.assertThat(result).isEqualTo("안녕");
        System.out.println("안녕 나는 짱구야");
    }


    @Test
    void helloMessage2(){
        String result = ms.getMessage("hello", new Object[]{"뚱땡아"}, null);
        Assertions.assertThat(result).isEqualTo("안녕");
    }

    @Test
    void helloMessage4(){
        String result = ms.getMessage("hello", null, null);
        Assertions.assertThat(result).isEqualTo("안녕");
        System.out.println("안녕 나는 짱구야");
    }


    @Test
    void helloMessage5(){
        String result = ms.getMessage("hello", null, null);
        Assertions.assertThat(result).isEqualTo("안녕");
        System.out.println("안녕 나는 짱구야");
    }


    @Test
    void helloMessage6(){
        String result = ms.getMessage("hello", null, null);
        Assertions.assertThat(result).isEqualTo("안녕");
        System.out.println("안녕 나는 짱구야");
    }


    @Test
    void helloMessage7(){
        String result = ms.getMessage("hello", null, null);
        Assertions.assertThat(result).isEqualTo("안녕");
        System.out.println("안녕 나는 짱구야");
    }



}
