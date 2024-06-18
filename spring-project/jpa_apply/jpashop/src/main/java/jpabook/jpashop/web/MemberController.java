package jpabook.jpashop.web;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /**
     * 회원가입 서비스 연결
     */
    @GetMapping("join")
    public String getMemberJoin(@ModelAttribute("member") MemberDto memberDto){
        return "member/join";
    }
    @PostMapping("join")
    public String memberJoin(@Validated @ModelAttribute MemberDto memberDto , BindingResult bindingResult){
        //Bean Validation 검증
        if (bindingResult.hasErrors()){
            return "member/join";
        }
        Member member = new Member();
        member.setAddress(new Address(memberDto.getCity() , memberDto.getStreet(), memberDto.getZipcode()));
        member.setName(memberDto.getName());
        //order은 나중에 id는 persist()할때 자동 생성 왜냐하면 @GeneratedValue(strategy = IDENTITY)이므로
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("list")
    public String getMemberList(Model model){
        List<Member> members = memberRepository.findAll(); //모든 회원에 대한 정보를 보여줘야하니 get에서 회원 리스트를 던짐
        model.addAttribute("members", members);
        return "member/list";//뷰에서 th:each 같은 걸로 반복해서 member 리스트 출력? 끝? 그럼 뭐 post에서 할거없노?
    }



}
