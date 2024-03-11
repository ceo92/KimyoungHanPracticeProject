package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    //당연히 등록해줘야겠찌 설정 정보 @Configuration에 ? ㅎㅎ
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("MyHandlerExceptionResolver");
        if (ex instanceof IllegalArgumentException) {
            try {
                log.info("IllegalArgumentException resolver to 400"); //IllegalArgumentException이 발생하면 무조건 400오류로 변환
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());//여기서 예외 먹음 sendError로 변환하니 ㅇㅇ
                return new ModelAndView();//그냥 ModelAndView 객체 반환하면 정상적으로 WAS까지 리턴됨
            } catch (IOException e) {
                log.error("resolver ex", e); //error일 경우 {} 안 해도 됨 그냥 쉼표 찍음녀 됨
            }
        }

        if (ex instanceof RuntimeException) {
            try {
                log.info("RuntimeException resolver to 500"); //IllegalArgumentException이 발생하면 무조건 400오류로 변환
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());//여기서 예외 먹음 sendError로 변환하니 ㅇㅇ
                return new ModelAndView();//그냥 ModelAndView 객체 반환하면 정상적으로 WAS까지 리턴됨
            } catch (IOException e) {
                log.error("resolver ex", e); //error일 경우 {} 안 해도 됨 그냥 쉼표 찍음녀 됨
            }
        }


        return null;
    }
}
