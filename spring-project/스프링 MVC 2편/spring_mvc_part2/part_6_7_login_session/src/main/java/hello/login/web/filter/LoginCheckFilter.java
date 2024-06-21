package hello.login.web.filter;

import hello.login.web.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {


    private static final String[] whileList = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        //요청 uri의 경로 받아놓음
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            log.info("인증 체크 필터 시작{}", requestURI);
            //로그인 체크해야되는 경로인지 아닌지 체크 , 화이트리스트에 해당 안 되면 해당 체크 거쳐서 로그인 여부 따져야됨
            if (isLoginCheckPath(requestURI)){
                log.info("인증 체크 로직 실행 {}" , requestURI);
                HttpSession session = httpRequest.getSession(false); //false : 최대한 세션 만들면 안 되므로
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){

                    log.info("미인증 사용자 요청 {}" , requestURI);
                    //미인증 사용자일 경우 로그인으로 화면으로 redirect
                    //성공 시에는 지정된 url로 반환하게 유도 , 로그인 성공했을때 다시 접근하려 했던 페이지로 리다이렉트 이동 ! , 다시 찾아오는 거 번거로움
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI); //이건 그냥 지정한 url로 보내는데 쿼리 파라미터를 지정한 것 뿐
                    return; //미인증 사용자는 다른으로 진행하는 것 막기 위해 이거 지정
                    //sendRedirect이후 return통해 끝냄 , 더 이상 호출 X
                }
            }
            chain.doFilter(request , response);
        }catch (Exception e){
            throw e;//예외 로깅 가능 , 하지만 톰캣까지 예외를 보내줘야함 , 안 보내면 정상인 것처럼 동작하니
        }finally {
            log.info("인증 체크 필터 종료"); //뭐가 됐든 finally는 무조건 호출
        }


    }

    /**
     * 화이트 리스트일 경우 인증 체크 X
     * 해당 필터를 적용 안 하는 것 기본 페이지나 로그인 페이지 같은 데는 꼭 회원이 아니더라도 당연히 접근하게 해야하니 ㅋㅋ
     */
    private boolean isLoginCheckPath(String requestURI){
        //요청 uri가 해당 리스트에 속하지 않은 경우 체크(필터)를 해야하니 NOT조건
        return !PatternMatchUtils.simpleMatch(whileList , requestURI);
    }


}
