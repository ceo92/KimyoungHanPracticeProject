package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request , HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String s = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        response.getWriter().write(s);
        log.info("글자는: {} 이다",s);

    }
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream , Writer writer) throws IOException {
        String s = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        writer.write(s);
        log.info("글자는: {} 이다",s);

    }
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(RequestEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();
        HttpMethod method = httpEntity.getMethod();
        Type typeName = httpEntity.getType();

        log.info("messageBody={} , method = {} , typeName={}",messageBody , method , typeName);
        return new ResponseEntity<>("ok" , HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody = {}" , messageBody);
        //걍 @RequestBody 어노테이션으로 파라메터 만들어놓으면 , 해당 파라메터에 바디가 string으로 저장 ㅋㅋㅋ
        //@ResponseBody도 있다. 그대로 api 방식으로 http 응답 메세지 바디 부분에 꽂아줌
        //요청은 @RequestBody , 응답은 @ResponseBody 존재 !
        return "ok";
    }


}
