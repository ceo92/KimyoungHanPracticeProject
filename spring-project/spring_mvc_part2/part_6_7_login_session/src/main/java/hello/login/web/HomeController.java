package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;
    //@GetMapping("/")
    public String homeLogin() {
        return "home";
    }


    //@GetMapping("/")
    public String loginHomeControllerV1(@CookieValue(name = "memberId" , required = false) Long memberId , Model model){
        if (memberId == null){
            return "home"; //쿠키가 없으면 사용자 로그인 또한 안됐단 뜻이니 홈 페이지 이동
        }

        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null){
            return "home"; //DB에도 해당 ID의 회원 없을 수도 있으니 이렇게 로직 지정
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }


    //@GetMapping("/")
    public String loginHomeControllerV2(HttpServletRequest request , Model model){
        //요청 쿠키의 세션id를 통해 세션 저장소에 있는 멤버 조회
        Member member = (Member)sessionManager.getSession(request);

        // 반환값 null이었으면 요청 쿠키에 대한 세션 없단 뜻이니 다시 홈으로 반환
        if (member == null){
            return "home";
        }

        //조회 성공 시 해당 회원 정보를 갖고 로그인 성공 페이지로 이동
        model.addAttribute("member", member);
        return "loginHome";
    }

    //@GetMapping("/")
    public String loginHomeControllerV3(HttpServletRequest request , Model model){
        //세션은 메모리를 쓰는 것이니 필요할 때만 생성해야됨 , true로 하면 그냥 홈페이지에 접근만 해도 세션이 생성됨 , 메모리 낭비이니 false 유지 !
        //false로 해야 홈페이지 접근했을 때 세션이 굳이 생성이 안 되므로 메모리 낭비가 안 되게됨 ,
        HttpSession session = request.getSession(false);
        if (session == null){
            return "home";
        }


        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null){
            return "home";
        }
        //여기 예외 로직까지 1. 가져올 세션이 하나도 없으면 null 반환 후 home 이동 , 2. 세션 중 내가 원하는 loginMember에 관한 세션 없으면 null 반환 후 홈 이동
        //조회 성공 시 해당 회원 정보를 갖고 로그인 성공 페이지로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    //@GetMapping("/")
    public String loginHomeControllerV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER , required = false) Member loginMember , Model model){
        //지정된 이름의 세션이 있으면 가져옴 , 없으면 null 반환

        if (loginMember == null){
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String loginHomeControllerV3ArgumentResolver(@Login Member loginMember , Model model){
        //지정된 이름의 세션이 있으면 가져옴 , 없으면 null 반환

        if (loginMember == null){
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }




}