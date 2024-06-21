package hello.springmvc.basic.response;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Slf4j
@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView modelAndViewV1(){
        ModelAndView mv = new ModelAndView("response/hello").addObject("data", "hello");
        return mv;

    }

    @RequestMapping("/response-view-v2")
    public String modelAndViewV2(Model model){
        model.addAttribute("data","suhyun");
        return "response/hello";

    }

    @RequestMapping("/response/hello")
    public void modelAndViewV3(Model model){
        model.addAttribute("data","kyungmin");
    }


}
