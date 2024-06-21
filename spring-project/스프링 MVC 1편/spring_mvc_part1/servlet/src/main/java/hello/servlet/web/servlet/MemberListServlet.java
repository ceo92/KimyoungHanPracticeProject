package hello.servlet.web.servlet;


import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//모든 회원 출력
@WebServlet(name = "memberListServlet" , urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {
    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //여기선 DB에 저장된 회원들을 전부 가져오는 것임 그동안 열심히 클라이언트가 폼에 입력 후 submit 했던 내용 ㅇㅇ
        List<Member> members = memberRepository.findAll();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter w = response.getWriter();
        w.write("<html>");
        w.write("<head>");
        w.write(" <meta charset=\"UTF-8\">");
        w.write(" <title>Title</title>");
        w.write("</head>");
        w.write("<body>");
        w.write("<a href=\"/index.html\">메인</a>");
        w.write("<table>");
        w.write(" <thead>");
        w.write(" <th>id</th>");
        w.write(" <th>username</th>");
        w.write(" <th>age</th>");
        w.write(" </thead>");
        w.write(" <tbody>");

         /*w.write(" <tr>");
         w.write(" <td>1</td>");
         w.write(" <td>userA</td>");
         w.write(" <td>10</td>");
         w.write(" </tr>");*/

        for (Member member : members) {
            w.write("       <tr>");
            w.write("           <td>" + member.getId() + "</td>");
            w.write("           <td>" + member.getUsername() + "</td>");
            w.write("           <td>" + member.getAge() + "</td>");
            w.write("       </tr>");
        }
        //이렇게 하면 DB에 저장된 member들을 for문으로 전부 가져와서 td로 전부 출력한다
        w.write(" </tbody>");
        w.write("</table>");
        w.write("</body>");
        w.write("</html>");


    }
}
