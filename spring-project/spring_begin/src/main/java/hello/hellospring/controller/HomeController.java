package hello.hellospring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//메인 페이지를 다루는 컨트롤러(내부 백앤드 자바 클래스)
//안드로이드 프로그래밍은 자바와 xml의 호흡이었다면 얘는 자바와 html 호흡이네
@Controller
public class HomeController {
    @GetMapping("")
    public String home(){
        return "home";
    }

}
