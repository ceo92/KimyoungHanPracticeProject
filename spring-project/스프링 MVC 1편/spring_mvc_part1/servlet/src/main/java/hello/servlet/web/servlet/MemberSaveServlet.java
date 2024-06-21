package hello.servlet.web.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;

@WebServlet(name = "memberSaveServlet" , urlPatterns = "/servlet/members/save")
public class MemberSaveServlet extends HttpServlet {

    ObjectMapper objectMapper = new ObjectMapper();
    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //폼 서블릿과 저장 서블릿 나눴네 SRP 이게 객체 지향인가...
        //여기가 내가 처음에 개발한 그 저장 부분이네 HTML 부분은 어차피 basic/hello-form.html에서 개발했으므로 !
        //basic/hello-form.html = MemberFromServlet 둘 다 같은 형태 ㅇㅇ 자바 코드로 했냐 html로 했냐 차이 !


        String username = request.getParameter("username"); //username
        int age = Integer.parseInt(request.getParameter("age")); //age
        //요청 메세지 파라메터 꺼내기

        Member member = new Member(username , age); //username  , age 객체화
        memberRepository.save(member); //DB에 저장
        //비즈니스 로직

        //결과 동적 HTML로 응답
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter w = response.getWriter();
        w.write("<html>\n" +
                "<head>\n" +
                " <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "성공\n" +
                "<ul>\n" +
                " <li>id="+member.getId()+"</li>\n" +
                " <li>username="+member.getUsername()+"</li>\n" +
                " <li>age="+member.getAge()+"</li>\n" +
                "</ul>\n" +
                "<a href=\"/index.html\">메인</a>\n" +
                "</body>\n" +
                "</html>");

        //이건 JSON 로직
        /*
        String s = objectMapper.writeValueAsString(memberRepository.findById(member.getId())); //JSON으로 변환
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().println(s);


         */



    }
}
