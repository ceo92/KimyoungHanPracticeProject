package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface MyHandlerAdapter {
    //컨트롤러가 넘어왔을 때 이 컨트롤러를 이 어댑터가 지원할 수 있는지 여부
    boolean supports(Object handler);

    ModelView handle(HttpServletRequest request , HttpServletResponse response ,Object handler) throws ServletException , IOException;

    //ControllerV3를 지원하는 어댑터를 만들어보자
}
