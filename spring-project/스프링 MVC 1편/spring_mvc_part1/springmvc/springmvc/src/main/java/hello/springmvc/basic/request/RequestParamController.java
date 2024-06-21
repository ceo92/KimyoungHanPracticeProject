package hello.springmvc.basic.request;


import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {


    //그냥 순수 서블릿 방식 쓰려나보네
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        log.info("username ={}",username);
        log.info("age={}",age);
        PrintWriter writer = response.getWriter();
        writer.write("username= "+username+"age =  "+age);
    }

    @ResponseBody //RestController로 API방식으로 바디에 그대로 문자 꽂아줬던 것처럼 각 메서드에 @ResponseBody적어주면 return 값을 논리적 뷰 이름이 아닌 문자 그대로 바디에 꽂아줌
    @RequestMapping("/request-param-v2")
    public String requestParamv2(@RequestParam("username") String memberName, @RequestParam("age") int memberAge){
        log.info("username = {} , age = {}",memberName , memberAge);
        return "ok";
    }

    @ResponseBody //RestController로 API방식으로 바디에 그대로 문자 꽂아줬던 것처럼 각 메서드에 @ResponseBody적어주면 return 값을 논리적 뷰 이름이 아닌 문자 그대로 바디에 꽂아줌
    @RequestMapping("/request-param-v3")
    public String requestParamv3(@RequestParam String username, @RequestParam int age){
        log.info("username = {} , age = {}", username , age);
        return "ok";
    }

    @ResponseBody //RestController로 API방식으로 바디에 그대로 문자 꽂아줬던 것처럼 각 메서드에 @ResponseBody적어주면 return 값을 논리적 뷰 이름이 아닌 문자 그대로 바디에 꽂아줌
    @RequestMapping("/request-param-v4")
    public String requestParamv4(String username, int age){
        log.info("username = {} , age = {}", username , age);
        return "ok";
    }

    @ResponseBody //RestController로 API방식으로 바디에 그대로 문자 꽂아줬던 것처럼 각 메서드에 @ResponseBody적어주면 return 값을 논리적 뷰 이름이 아닌 문자 그대로 바디에 꽂아줌
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username, @RequestParam(required = false) Integer age){
        log.info("username = {} , age = {}", username , age);
        return "ok";
    }

    @ResponseBody //RestController로 API방식으로 바디에 그대로 문자 꽂아줬던 것처럼 각 메서드에 @ResponseBody적어주면 return 값을 논리적 뷰 이름이 아닌 문자 그대로 바디에 꽂아줌
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true , defaultValue = "guest") String username, @RequestParam(required = false , defaultValue = "-1") int age){
        log.info("username = {} , age = {}", username , age);
        return "ok";
    }

    @ResponseBody //RestController로 API방식으로 바디에 그대로 문자 꽂아줬던 것처럼 각 메서드에 @ResponseBody적어주면 return 값을 논리적 뷰 이름이 아닌 문자 그대로 바디에 꽂아줌
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String , Object> paramMap){
        log.info("username = {} , age = {}", paramMap.get("username") , paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
        log.info("username={} , age={}" , helloData.getUsername() , helloData.getAge());
        return "ok";

    }







}
