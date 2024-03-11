package hello.login.web.interceptor;

import hello.login.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}" , requestURI);
        HttpSession session = request.getSession(false);
        if (session==null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
            /**
             * 1. 로그인 페이지로 보낸다
             * 2. 정상 로그인 시 기존 페이지로 리다이렉트
             */
            log.info("미인증 사용자 요청");
            //지정된 url로 보냄
            response.sendRedirect("/login?redirectURL="+requestURI);
            return false;
            //인터셉터는 화이트리스트를 인터셉터 등록할 때 다 해줄 수 있음
            //즉 필터처럼 복잡한 화이트리스트 검증 로직 작성 안 해도 됨
            // 근데 그러면 필터도

        }

        return true;
    }




}
