package hello.login.web.login;


import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;
    private final SessionManager sessionManager;
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
        return "login/loginForm";
    }

    //@PostMapping("/login")
    public String loginV1(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult , HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword()); //받는 것만 LoginForm으로 받고 LoginForm에서의 값을 꺼내서 실제 멤버에서의 처리 ㅇㅇ
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리 TODO

        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);
        return "redirect:/";
    }

    //@PostMapping("/login")
    public String loginV2(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult , HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리 TODO
        sessionManager.createSession(loginMember, response); //로그인 사용자에 대한 세션 생성 => 응답 메시지 쿠키 지정

        return "redirect:/";
    }
    //@PostMapping("/login")
    public String loginV3(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult , HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리 TODO
        /*
        getSession() : 모든 과정이 자동으로 다 됨
        true : 있으면 기존 세션 반환 , 없으면 세션 생성해서 반환
        false : 있으면 기존 세션 반환 , 없으면 생성 안 하고 null 반환
         */
        HttpSession session = request.getSession(true);

        //setAttribute() : 세션에 보관하고싶은 객체 담기(세션 이름 , 로그인 정보 객체)
        session.setAttribute(SessionConst.LOGIN_MEMBER , loginMember);
        /*Map<String, Map<String, Object>> aa = new HashMap<>();
        Map<String , Object> kmSession = new HashMap<>();
        kmSession.put("session_name" , new Member());
        aa.put(UUID.randomUUID().toString() , kmSession);*/
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginV4(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult
            , @RequestParam(defaultValue = "/") String redirectURL
            , HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리 TODO
        HttpSession session = request.getSession(true);

        session.setAttribute(SessionConst.LOGIN_MEMBER , loginMember);

        return "redirect:" + redirectURL; //@RequestParam으로 받는 파라메터가 있으면 지정된 곳으로 uri로 리다이렉트하고 없으면 그냥 홈으로 반환
    }



    //@PostMapping("/logout")
    public String logoutV1(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    //@PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expire(request); //요청된 쿠키에 해당되는 세션 정보가 존재하면 해당 세션 삭제 아니면 전부 null 반환
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        //false로 해야 세션 없을때 굳이 세션 생성이 안 되고 null이 반환돼서 세션이 무효화 될 수 있게 로직 짤 수 있음
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate(); //지정된 세션이 통째로 다 날라감
        }
        return "redirect:/";
    }


    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
