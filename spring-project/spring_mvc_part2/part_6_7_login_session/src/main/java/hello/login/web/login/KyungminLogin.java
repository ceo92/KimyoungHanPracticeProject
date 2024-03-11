package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lovekm")
@RequiredArgsConstructor
public class KyungminLogin {
    private final LoginService loginService;
    @GetMapping
    public String getLovekm(@ModelAttribute LoginForm loginForm){
        return "lovekm";
    }

    @PostMapping
    public String loveKmV1(@Validated @ModelAttribute LoginForm loginForm , BindingResult bindingResult){
        //필드 오류 검증
        if (bindingResult.hasErrors()){
            return "lovekm";
        }
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword()); //받는것만 LoginForm실제 처리는 Member로 !
        //전역 오류 검증
        if (loginMember==null){
            bindingResult.reject("loginError" , "I require you to check id/pw");
            return "lovekm";
        }

        return "lovekmsuccess";

    }
}
