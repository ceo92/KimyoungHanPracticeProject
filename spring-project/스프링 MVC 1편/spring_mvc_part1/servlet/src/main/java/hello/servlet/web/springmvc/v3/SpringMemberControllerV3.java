package hello.servlet.web.springmvc.v3;


import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @GetMapping("/new-form")
    public String newForm(){
        return "new-form";
    }// 스프링 MVC는 굳이 ModelAndView 반환 안해도 굉장히 인터페이스로 유연하게 설계돼있으니
    //스프링이 해당 문자를 뷰 논리적 이름으로 인식하고 다음 프로세스 진행

    @PostMapping("/save")
    public String members(@RequestParam("username") String username , @RequestParam("age") int age , Model model) {
        //스프링 mvc는 request , response 뿐만 아니라 파라메터를 내용을 직접 받을 수도 있음 int로 두면 형변환도 자동으로 해줌 미쳤네 ㅋㅋ
        // 모델도 받을 수 있음ㅋㅋ ㅅㅂ 그냥 스프링이 다 해줌 씨발 ㅋㅋ
       // 즉 지저분하게 Integer 파싱하거나 서블릿 구현해서 서블릿에서 파라메터 꺼낼 필요도 사라짐
        Member member = new Member(username , age);
        memberRepository.save(member);
        model.addAttribute("member" , member);
        return "save-result";


    }
    @GetMapping
    public String save(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members",members);
        return "members";

    }

}