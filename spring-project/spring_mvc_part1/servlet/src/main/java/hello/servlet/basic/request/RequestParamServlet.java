package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "requestParamServlet" , urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //전체 조회
        request.getParameterNames().asIterator().forEachRemaining(parameterName-> System.out.println(parameterName +": "+request.getParameter(parameterName)));
        
        //단일 파라메터 조회
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);
        
        //복수 파라메터 조회
        String[] usernames = request.getParameterValues("username");
        for (String s : usernames) {
            System.out.println("s = " + s);
        }
    }
}
