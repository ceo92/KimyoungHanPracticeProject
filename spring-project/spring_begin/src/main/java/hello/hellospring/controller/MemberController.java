package hello.hellospring.controller;


import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


//메인이 아닌 회원 조회 , 관리 , 저장 등의 회원 관련 동작 페이지의 조작을 위한 컨트롤러
@Controller
public class MemberController {
    //서비스와 의존관계 맺는 역할
    private final MemberService memberService;
    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }

    //메인페이지 <a>태그에서 membersnew로 넘어가게 했으니 url에서 members/new를 입력하게 강제. 해당 매핑된 메서드 작동 시 그냥 createMemberForm 뷰로 넘어가게함
    @GetMapping("/members/new") //컨트롤러에서 @GetMapping들을 매핑 뒤 각 이름이 입력될때까지 대기 입력되면 해당 매핑된 컨트롤러가 실행되며 뷰로 넘어감
    public String createForm(){
        return "members/createMemberForm";
    }


    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getSex());//MemberForm은 걍 name 도메인 클래스 이름을 설정 및 반환하는 클래스임
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
