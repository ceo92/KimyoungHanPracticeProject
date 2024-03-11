package hello.springmvc.basic.request;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j //로그 변수 초기화
@RestController //return 값을 뷰 이름이 아닌 문자 그대로 HTML 바디 부분에 박음
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request , //서블릿 받고
                          HttpServletResponse response ,
                          HttpMethod httpMethod , //HTTP 메서드 받을 수 있음
                          Locale locale, //언어 정보 받을 수 있다
                          @RequestHeader MultiValueMap<String , String> headerMap, //모든 헤더 한번에 다 받기
                          @RequestHeader("host")String host, //특정 헤더 하나만 받기(Host는 필수 헤더)
                          @CookieValue(value = "myCookie" , required = false)String cookie){ //사용자의 쿠키정보를 받을 수 있음 이름은 세션id는 myCookie로 false니 없어도됨

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);
        return "ok";
    }
}
