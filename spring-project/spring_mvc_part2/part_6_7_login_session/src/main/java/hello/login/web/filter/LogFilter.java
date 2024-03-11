package hello.login.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    //HTTP 요청이 올 때마다 doFilter 우선 호출
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try{
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(request , response); //다음 필터 있으면 해당 필터 호출해주고 없으면 서블릿이 호출 , 그냥 필터 체인 그거임
            //당연히 요청 => WAS  => 필터1 => 필터2 => 필터3 => 서블릿 => 컨트롤러가 기본 동작과정이니 필터 있는지 우선 보고 없으면 서블릿을 호출해주는 게 맞음
        }catch(Exception e){
            throw e;
        }finally {
            log.info("RESPONSE [{}] [{}]" , uuid , requestURI);
        }


    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
