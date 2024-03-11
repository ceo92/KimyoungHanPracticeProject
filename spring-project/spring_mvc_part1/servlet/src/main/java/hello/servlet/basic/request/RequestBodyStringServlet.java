package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@WebServlet(name = "requestBodyStringServlet" , urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        //메세지 바디의 내용을 바이트코드로 얻을 수 있음
        //바이트코드를 String 즉 우리가 알아볼 수 있게 바꾸려면 다음과 같이 한다
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        // 항상 바이트 코드 <=> 문자 변환할 땐 어떤 인코딩 정보인지 알려줘야됨, 대부분 UTF-8 씀
        System.out.println("messageBody = " + messageBody);
        response.getWriter().write("ok");

        ServletInputStream inputStream1 = request.getInputStream();


    }
}
