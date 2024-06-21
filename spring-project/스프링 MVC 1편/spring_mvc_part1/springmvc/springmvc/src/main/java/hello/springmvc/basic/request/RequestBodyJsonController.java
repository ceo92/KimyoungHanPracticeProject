package hello.springmvc.basic.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request , HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody = {}",messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={} , age={}" , helloData.getUsername() , helloData.getAge());

        response.getWriter().write("ok");

    }

    @PostMapping("/request-body-json-v2") // api 방식은 바디에서 데이터를 가져오거나 삽입하는 것 => 즉 바디가 있어야 됨 => 바디가 있으려면 GET이면 안 됨 , 적어도 POST => 즉 JSON일때 Content-Type은 application/json이고  @ResponseBody 잘 쓰려면 Accept: applicatio/n
    @ResponseBody
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody = {}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={} , age={}" , helloData.getUsername() , helloData.getAge());
        return "ok";

    }

    @PostMapping("/request-body-json-v3")
    @ResponseBody
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {

        log.info("username={} , age={}" , helloData.getUsername() , helloData.getAge());

        return "ok";

    }


    @PostMapping("/request-body-json-v5")
    @ResponseBody
    public HelloData helloData(HttpEntity<HelloData> messageBody){
        HelloData helloData = messageBody.getBody(); //HttpEntity는 제네릭을 직접 만든 객체로 하면 JSON 컨버터가 실행되면서 찾아짐
        log.info("username = {} , age = {}",helloData.getUsername() , helloData.getAge());
        return helloData;
    }





}
