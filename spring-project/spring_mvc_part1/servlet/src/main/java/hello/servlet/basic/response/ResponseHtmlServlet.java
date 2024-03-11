package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHtmlServlet",urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.printf("<div><h1><b>안녕??</div>\n");
        writer.println("</body>");
        writer.println("</html>");

        //와 ㅅㅂ 서블릿 좆같긴 하네 그래서 JSP 나왔구나 바디 부분 데이터 HTML이면 이렇게 구성함? ㅅㅂ JSP나 타임리프는 뷰템플릿으로 바로바로 될 텐데
    }
}
