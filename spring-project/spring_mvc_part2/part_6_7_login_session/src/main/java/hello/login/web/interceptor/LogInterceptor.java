package hello.login.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "log-id";

    //모든 요청의 로그를 찍어야됨
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI= request.getRequestURI();
        String logId = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, logId);


        //@RequestMapping사용하는 경우의 핸들러 타입 : HandlerMethod
        //정적 리소스 : ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            //hm을 통해 컨트롤러의 api들 꺼낼 수 있음
        }

        log.info("REQUEST [{}][{}][{}]" , logId , requestURI , handler);
        //handler은 굉장히 유연하게 만듬 그래서 어댑터를 거쳐서 모든 핸들러를 다 받을 수 있게 구현한 거잖아 클라이언트가 여러 타입을 주입할 수 있었지
        //핸들러 어댑터가 알아서 다 처리해주니 아무거나 들어갈 수 있으니 Object 타입임
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle = [{}]" , modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}]",logId , requestURI);
        if (ex != null){
            log.error("afterCompletion", ex);
        }

    }
}

