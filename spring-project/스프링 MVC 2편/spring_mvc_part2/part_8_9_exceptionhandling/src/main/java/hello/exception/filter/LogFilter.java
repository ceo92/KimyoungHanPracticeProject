package hello.exception.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        try {
            log.info("FILTER REQUEST  [{}][{}][{}]", uuid, request.getDispatcherType(),
                    requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.info("ERROR EXIST!!");
            throw e;
        } finally {
            log.info("FILTER RESPONSE [{}][{}][{}]", uuid, request.getDispatcherType(),
                    requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}