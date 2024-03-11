package hello.login.web.filter;

import hello.login.web.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginCheckFilterClone implements Filter {

    private static String[] whileList = {"/", "/login", "/logout", "/members/add", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        try {
            if (isRequiredToCheckURI(requestURI)) {
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    //상품 관리 페이지에 비인증 사용자가 접근하려 했을 시 /login/redirectURL=+requestURI 경로로 보냄
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                    // 더 이상 로직 진행 안 하고 종료
                    return;
                }

            }
            chain.doFilter(request, response);
        }catch (Exception e){
            throw e;
        }
        finally {

        }

    }
    private boolean isRequiredToCheckURI(String requestURI){
        return !PatternMatchUtils.simpleMatch(whileList , requestURI);
    }
}
