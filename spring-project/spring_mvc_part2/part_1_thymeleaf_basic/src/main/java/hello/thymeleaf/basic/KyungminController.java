package hello.thymeleaf.basic;


import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hello-kyungmin")
public class KyungminController {

    @GetMapping("/v1")
    public String methodV1(@ModelAttribute Oliveyoung oliveyoung){
        return "basic/kyungmin"; //GET 쿼리 파라미터 요청 / 동적 View 응답
    }

    @PostMapping("/v2")
    public String methodV2(@ModelAttribute Oliveyoung oliveyoung){
        return "basic/kyungmin"; //HTML Form POST 요청 / 동적 View 응답
    }


    @PostMapping("/v3")
    public String methodV3(@RequestBody Oliveyoung oliveyoung , Model model){
        model.addAttribute("oliveyoung" , oliveyoung);
        return "basic/kyungmin"; //API 요청 / 동적 View 응답
    }

    @GetMapping("/v4")
    @ResponseBody
    public Oliveyoung methodV4(@ModelAttribute Oliveyoung oliveyoung){
        return oliveyoung; //GET 쿼리 파라미터 요청 / API 응답
    }

    @PostMapping("v5")
    @ResponseBody
    public Oliveyoung methodV5(@ModelAttribute Oliveyoung oliveyoung){
        return oliveyoung; //POST HTML Form 요청 / API 응답
    }

    @PostMapping("v6")
    @ResponseBody
    public Oliveyoung methodV6(@RequestBody Oliveyoung oliveyoung){
        return oliveyoung; //API 요청 / API 응답
    }

    @GetMapping("/{age}")
    @ResponseBody
    public String aa(@PathVariable("age") int age){
        return String.valueOf(age);
    }

    @PostMapping("/test")
    public String abc(HttpSession session , @ModelAttribute("oliveyoung") Oliveyoung oliveyoung , Model model){
        List<Oliveyoung> oliveyoungList = new ArrayList<>();
        Map<Integer, Oliveyoung> oliveyoungMap = new HashMap<>();


        setListMap(oliveyoungList, oliveyoungMap);
        model.addAttribute("oliveyoungs", oliveyoungList);
        model.addAttribute("oliveyoungMap", oliveyoungMap);
        return "basic/kyungmin";
    }

        private static void setListMap(List<Oliveyoung> oliveyoungList, Map<Integer, Oliveyoung> oliveyoungMap) {
            oliveyoungList.add(new Oliveyoung("이경민", 24, "과장"));
            oliveyoungList.add(new Oliveyoung("김수현", 28, "부장"));
            oliveyoungList.add(new Oliveyoung("차정현", 32, "대리"));
            int i=0;
            for (Oliveyoung o : oliveyoungList) {
                oliveyoungMap.put(i++ , o);
        }
    }


    @Data //필드 쓸거면 무조건 @Data 하거나 @Getter , @Setter 하거나 게터세터 임의로 만들자 !
    //@Component("oliveyoung")
    static class Oliveyoung{

        private String name;
        private int age;
        private String rank;

        public Oliveyoung(String name, int age, String rank) {
            this.name = name;
            this.age = age;
            this.rank = rank;
        }
    }

    @Component("helloData")
    static class HelloData{
        public String helloData(String data){
            return "This is "+data;
        }
    }


}
