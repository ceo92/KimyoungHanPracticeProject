package hello.hellospring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HelloController {

    @GetMapping("hello") //웹에서 /hello 호출하면 이 메서드 호출해줌 GET은 보이는
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        //key :  data , value : hello!!
        return "hello";
    }
    @GetMapping("hello-mvc")
    public String helloMvc( @RequestParam(value = "large") String name , Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "Hello" + name;
    }

    static class Hello{
        private String good;

        public String getName() {
            return good;
        }
        public void setName(String good) {
            this.good = good;
        }
    }
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("Lazy") String lazy){
        Hello hello = new Hello();
        hello.setName(lazy);
        return hello;

    }

    @GetMapping("hello-km")
    @ResponseBody
    public Hello helloKm(){
        Hello hello = new Hello();
        hello.setName("abcd");
        return hello;
    }


    @GetMapping("hello-asdasd")
    @ResponseBody
    public Hello helloASDASD(@RequestParam("name")String name ){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }





}
