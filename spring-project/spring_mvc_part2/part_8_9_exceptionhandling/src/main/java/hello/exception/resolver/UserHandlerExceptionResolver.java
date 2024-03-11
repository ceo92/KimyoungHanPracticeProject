package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try{
            log.info("500이 아닌 400으로 바꿀 것임");
            String header = request.getHeader("Accept");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //응답 시 400
            if (header.equals(MediaType.APPLICATION_JSON_VALUE)){
                Map<String, Object> errorResult = new HashMap<>(); //json 형태로 만들기 위해 ex에 예외 클래스 , message에 오류메시지 지정
                errorResult.put("ex", ex.getClass());
                errorResult.put("message", ex.getMessage());
                String result = objectMapper.writeValueAsString(errorResult);//객체를 문자(json문자열)로 바꿔줘야함

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");

                response.getWriter().write(result); //http 응답 바디에 json 문자열이 들어감
                return new ModelAndView();
                //아무것도 안 넘기고 정상 출력 , 예외를 먹어버리고 정상적인 ㄹ턴위와 같은 형태로 정상 흐름으로 동작 ,response 에 담은 데이터 응답됨 정상적으로 ㅇㅇ
            }else {
                return new ModelAndView("error/500");
            }



        }catch (Exception e){
            log.error("resolver ex" , e);
        }
        return null;
    }
}
