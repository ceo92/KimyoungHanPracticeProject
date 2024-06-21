package hello.springmvc.basic.response;


import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Dispatcher;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.service.invoker.HttpMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
//@Controller
//@ResponseBody
@RestController
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2(){
        return new ResponseEntity<>("ok" , HttpStatus.OK);
    }
    //HttpEntity 방식 ㅇㅇ
    //@ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3(){
        return "ok";
    }

    //단순 텍스트 API @ResponseBody 응답

    @GetMapping("/response-body-string-v4")
    public ResponseEntity<HelloData> responseBodyV4(){
        HelloData helloData = new HelloData();
        helloData.setAge(20);
        helloData.setUsername("abc");
        return new ResponseEntity<>(helloData , HttpStatus.OK);
    }

    @GetMapping("/response-body-string-v6")
    public ResponseEntity<HelloData> responseBodyV6(){
        HelloData helloData = new HelloData();
        helloData.setAge(20);
        helloData.setUsername("abc");
        return new ResponseEntity<>(helloData , HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)//v4는 되지만 이 친구는 상태 코드 못 바꾸니 따로 어노테이션 제공
    //@ResponseBody
    @GetMapping("/response-body-string-v5")
    public HelloData responseBodyV5(){
        HelloData helloData = new HelloData();
        helloData.setAge(20);
        helloData.setUsername("abc");
        return helloData;

    }


    @ResponseBody
    @GetMapping("/kyungmin/hi/{userid}")
    public String kyungMin(@ModelAttribute HelloData helloData , @PathVariable String userid){
        log.info("userid = {} , username=  {} , age = {}",userid , helloData.getUsername() , helloData.getAge());
        return "OK";
    }


    @PostMapping("/kyungmin/hi/{userid}")
    public String kyungMin22(@RequestBody HelloData helloData , @PathVariable String userid , Model model){
        log.info("userid = {} , username=  {} , age = {}",userid , helloData.getUsername() , helloData.getAge());
        return "OK";
    }








}
