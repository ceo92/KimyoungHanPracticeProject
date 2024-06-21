package hello.servlet.web.frontcontroller.v1.controller;

import hello.servlet.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberFormControllerV1 implements ControllerV1 {


    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/view/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);//컨트롤러에서 뷰로 이동할 때 사용 , 여기서 발생된 값을 지정된 경로로 넘겨줄 떄??
        dispatcher.forward(request , response);// 실제 서블릿에서 JSP 호출하는 로직
        //기존이랑 똑같이 request , response 다 넘어오니 로직 그냥 MVC 패턴 썼을 때와 같음
    }
}
