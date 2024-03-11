package hello.servlet.web.servletmvc;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet" , urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/view/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);//컨트롤러에서 뷰로 이동할 때 사용 , 여기서 발생된 값을 지정된 경로로 넘겨줄 떄??
        //RequestDispatcher은 forward()를 써주기 위해 정의한 객체 / 서블릿 => jsp 하려는데 jsp의 위치 지정해준 것
        dispatcher.forward(request , response);// 실제 서블릿에서 JSP 호출하는 로직

        //  말 그대로 하나로 만들었던 걸 두 개로 분할? 폼도? 그러니까
        //mvc패턴은 항상 컨트롤러를 거쳐서 뷰로 들어가야됨 , 서블릿(비즈니스 로직)에서 할 게 없어도 무조건 컨트롤러에서 뷰로 넘기는 것
    }
}
