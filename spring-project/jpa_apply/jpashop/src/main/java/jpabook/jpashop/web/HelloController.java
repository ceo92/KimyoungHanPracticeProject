package jpabook.jpashop.web;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    /*@GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!");
        return "hello";
    }*/

    //홈에선 할게 없네 ㅇㅇ 뷰에서 어차피 location.href=로 전부 이동만 하니 ㅇㅇ 각 컨트롤러에서 post 작업 해줘야겠네 !
    @RequestMapping
    public String getHome(){
        return "home";
    }

    @GetMapping("/aa")
    public String aa(){
        return "aa";
    }



}
