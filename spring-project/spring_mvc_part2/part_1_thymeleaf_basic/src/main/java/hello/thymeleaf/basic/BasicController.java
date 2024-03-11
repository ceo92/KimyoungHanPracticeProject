package hello.thymeleaf.basic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("text-basic")
    public String textBasic(Model model){
        model.addAttribute("data" , "<b>Hello Spring</b>");
        return "basic/text-basic";
    }

    @GetMapping("text-unescaped")
    public String textUnescaped(Model model){
        model.addAttribute("data" , "<b>Hello Spring</b>");
        return "basic/text-unescaped";
    }

    @GetMapping("variable")
    public String variable(Model model){
        User userA = new User("kevin" , 10);
        User userB = new User("Antonio" , 20);


        List<User> userList = new ArrayList<>();
        userList.add(userA);
        userList.add(userB);

        Map<String , User> userMap = new HashMap<>();
        userMap.put("userA" , userA);
        userMap.put("userB" , userB);

        model.addAttribute("user" , userA);
        model.addAttribute("users" , userList);
        model.addAttribute("userMap", userMap);

        return "basic/variable";
    }

    @GetMapping("/basic-objects")
    public String aa(HttpSession session  , HttpServletRequest request , HttpServletResponse response ,HelloBean helloBean , Model model){
        session.setAttribute("sessionData" , "Hello Session");
        model.addAttribute("request" , request);
        model.addAttribute("response" , response);
        model.addAttribute("servletContext" , request.getServletContext());
        return "basic/basic-objects";
    }

    @GetMapping("/link")
    public String link(Model model){
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "basic/link";

    }

    @GetMapping("/literal")
    public String literal(Model model){
        model.addAttribute("data" , "Spring!");
        return "basic/literal";
    }


    @GetMapping("/operation")
    public String  operation(Model model){
        model.addAttribute("nullData" , null);
        model.addAttribute("data" , "Spring!");
        return "basic/operation";
    }

    @GetMapping("/attribute")
    public String attribute(){
        return "basic/attribute";
    }

    @GetMapping("/each")
    public String each(Model model){
        addUsers(model);
        return "basic/each";
    }


    @GetMapping("/condition")
    public String condition(Model model){
        addUsers(model);
        return "basic/condition";

    }

    @GetMapping("/comments")
    public String comments(Model model){
        model.addAttribute("data", "Spring!!");
        return "basic/comments";
    }

    @GetMapping("/block")
    public String block(Model model){
        addUsers(model);
        return "basic/block";
    }
    @GetMapping("/score")
    public String score(Model model){
        addStudents(model);
        addUsers(model);
        return "basic/practice";
    }

    @GetMapping("/javascript")
    public String javascript(Model model){
        model.addAttribute("user", new User("User\"A\"", 10));
        addUsers(model); //모델에 user 리스트 추가
        return "basic/javascript";
        //단일 유저 + 유저 리스트 모델에 추가 !
    }














    private void addUsers(Model model){
        List<User> userList = new ArrayList<>();
        userList.add(new User("userA", 10));
        userList.add(new User("userB", 20));
        userList.add(new User("userC", 30));
        model.addAttribute("users", userList);
    }


    private void addStudents(Model model){
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("studentA", 95));
        studentList.add(new Student("studentB", 85));
        studentList.add(new Student("studentC", 55));
        model.addAttribute("students", studentList);
    }





    @Component("helloBean")
    static class HelloBean{
        public String helloData(String data){
            return "hello" + data;
        }

    }
    @Data
    static class User{
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }


    @Data
    static class Student{
        private String name;
        private int score;

        public Student(String name , int score){
            this.name=name;
            this.score=score;
        }

    }
}
