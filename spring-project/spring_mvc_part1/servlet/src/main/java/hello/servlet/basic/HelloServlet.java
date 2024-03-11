package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet(name = "helloServlet" , urlPatterns = "/hello")//이게 실행
public class HelloServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        //요청 메세지 작업
        String username = request.getParameter("username");
        //username으로 지정하면 클라이언트가 url에서 쿼리파라미터에 username값을 입력하면 username키에 대한 값이 해당 변수에 전달
        System.out.println("username = " + username);


        //응답 메세지 가공
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        // 헤더
        response.getWriter().write("hello"+username);
        //바디
    }
    //사용자 요청이 오면 서블릿 객체가 호출되잖아 요청 받고 응답 처리 ㅇㅇ 그러니 서블릿 객체 쓰레드가 호출하면서 해당 객체가
    //  실행됨 한 요청엔
}
