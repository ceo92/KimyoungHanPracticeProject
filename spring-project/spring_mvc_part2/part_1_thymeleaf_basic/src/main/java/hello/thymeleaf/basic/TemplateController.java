package hello.thymeleaf.basic;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    @GetMapping("/fragment") //조각을 불러다 쓰는 기능이라고 해서 fragment
    public String template(){
        return "template/fragment/fragmentMain";
        //fragment는 조각을 불러다 쓰는 파일들이 존재한 폴더를 의미
    }


    @GetMapping("/layout")
    public String layout(){
        return "template/layout/layoutMain";
        //우리 사이트에 공통된 헤더 layout을 template/layout/base에 지정할 것 loyoutMain은 그 헤더를 쓰는 파일인 거고 !

    }

    @GetMapping("/layoutExtend")
    public String layoutExtend(){
        return "template/layoutExtend/layoutExtendMain";
    }




}
