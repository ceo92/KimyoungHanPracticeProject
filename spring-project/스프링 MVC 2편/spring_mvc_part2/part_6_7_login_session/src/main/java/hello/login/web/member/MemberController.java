package hello.login.web.member;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute Member member){
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") Member member , BindingResult bindingResult){
        //전역 검증 및 기존 페이지 리턴 로직 , 필드 검증은 이미 어노테이션으로 끝남

        //리턴 로직
        if (bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "members/addMemberForm";
        }
        //성공 로직 , 걍 예제니까 직접 검증함 실무에선 수정, 가입 분리되어있지 않아도 다른 객체로 받아야함 , 약관같은 많은 정보 주입되니 !
        memberRepository.save(member);
        return "redirect:/";
    }




}
