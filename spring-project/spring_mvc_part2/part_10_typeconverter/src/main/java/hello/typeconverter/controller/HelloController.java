package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.format.Formatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {


    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request){
        String data = request.getParameter("data");
        int intValue = Integer.parseInt(data);
        System.out.println("intValue = " + intValue);
        return "ok";
    }

    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data){
        System.out.println("data = " + data);
        return "ok";
    }

    @GetMapping("/hello-v3")
    public String helloV3(@RequestParam IpPort data){
        System.out.println("data.getIp() = " + data.getIp());
        System.out.println("data.getPort() = " + data.getPort());
        return "ok";
    }










    /*@GetMapping("/hello-ex")
    public String helloEx(HttpServletResponse response){
        throw new IllegalArgumentException("Rainbow Exception Accrue");
    }*/




}
