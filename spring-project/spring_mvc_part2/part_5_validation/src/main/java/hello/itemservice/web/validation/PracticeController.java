package hello.itemservice.web.validation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/hello-practice")
@Slf4j
public class PracticeController {

    @GetMapping
    public String get(Model model){
        model.addAttribute("employee" , new Employee());
        return "validation/practice";
    }

    @PostMapping
    public String post(@ModelAttribute Employee employee , BindingResult bindingResult , RedirectAttributes redirectAttributes){
        if (!StringUtils.hasText(employee.getName())){
            bindingResult.addError(new FieldError("employee" , "name" , "직원 이름은 꼭 입력하기"));
        }
        if ((employee.getAge() == null) || (employee.getAge()<20)){
            bindingResult.addError(new FieldError("employee" , "age" , "나이는 20살 넘어서 !"));
        }

        if (bindingResult.hasErrors()){
            return "validation/practice";
        }
//        redirectAttributes.addAttribute()
        return "validation/success";
    }

    @GetMapping("/test")
    public String aax(Model model){
        model.addAttribute("major" , new Major());
        return "validation/major";
    }

    @PostMapping("/test")
    @ResponseBody
    public Major aa(@Validated @RequestBody Major major , BindingResult bindingResult){
        log.info("이것은 연습용 컨트롤러입니다.");
        log.info("error = {}" , bindingResult.getAllErrors());
        return major;
    }

    @Data
    static class Employee{
        private String name;
        private Integer age;
    }

    @Data
    static class Major{
        @NotNull
        private Integer professorName;


        @NotNull
        private Integer majorName;
    }

}
