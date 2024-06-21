package hello.servlet.web.frontcontroller.v1;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV1 {

    //서블릿과 똑같은 형태의 로직을 만듬 이름만 다름
    void process(HttpServletRequest request , HttpServletResponse response) throws ServletException  , IOException;

    //이 컨트롤러 인터페이스를 통해 회원 폼 , 저장 , 리스트 컨트롤러를 구현할 것임 , 객체 지향의 꽃 다형성 적극 활용
    //다형성에 의존해서 편리하게 인터페이스에서 객체 호출



}
