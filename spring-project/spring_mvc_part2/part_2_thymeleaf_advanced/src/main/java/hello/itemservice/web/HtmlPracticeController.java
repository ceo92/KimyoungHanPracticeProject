package hello.itemservice.web;


import com.fasterxml.jackson.annotation.JsonProperty;
import hello.itemservice.domain.item.Item;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/savage")
public class HtmlPracticeController {

    @ModelAttribute("hobbies")
    public Map<String , String> hobbies(){
        Map<String , String> hobbies = new LinkedHashMap<>();
        hobbies.put("BADMINTON", "베드민턴");
        hobbies.put("GAME", "게임");
        hobbies.put("DRIVING", "드라이브");
        return hobbies;
    }


    @ModelAttribute("educations")
    public LastEducation[] educations(){
        return LastEducation.values();
    }




    @GetMapping("/v1")
    public String divTestV1(){
        return "/practice/v1";
    }

    @GetMapping("/v2")
    public String tableTestV2(Model model){
        model.addAttribute("item", new Item("itemA", 2000, 2));
        return "/practice/v2";
    }

    @GetMapping("/v3")
    public String getFormV3(Model model){
        model.addAttribute("person" , new Person());
        return "/practice/v3";
    }

    @PostMapping("/v3")
    @ResponseBody
    public String postFormV3(@ModelAttribute Person person){
        log.info("marry : {}",person.getMarry());
        return "저장 완료 !!";
    }


    @GetMapping("/v4")
    public String getFormV4(Model model){
        model.addAttribute("person" , new Person());
        return "/practice/v4";
    }

    @PostMapping("/v4")
    @ResponseBody
    public Person postFormV4(@ModelAttribute("person") Person person){
        return person;
    }

    @Data
    static class Person{
        private String name;
        private String job;
        private Integer age;

        private Boolean marry; //어차피 ModelAttribute는 파라메터를 setter로 찾으니 굳이 생성자에 지정 안 해도 됨 !!

        private List<String> hobbies;

        private LastEducation education;

        private String salary;

        public Person(String name , String job , Integer age){
            this.age=age;
            this.job=job;
            this.name=name;
        }
        public Person(){

        }
    }





}
