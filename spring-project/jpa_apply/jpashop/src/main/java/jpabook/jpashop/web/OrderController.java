package jpabook.jpashop.web;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/")
public class OrderController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;
    @GetMapping("order")
    public String orderForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members" , members);
        model.addAttribute("items", items);
        return "order/order";
    }

    @PostMapping("order")
    public String postOrder(@ModelAttribute OrderDto orderDto){
        orderService.order(orderDto.getMemberId() , orderDto.getItemId(),  orderDto.getCount());
        return "redirect:/order/list";
    }


    @GetMapping("list")
    public String getOrderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/list";

    }

   /* @PostMapping("list")
    public String postOrderList(){

    }*/

    @Getter @Setter
    static class OrderDto{
        private Long memberId;
        private Long itemId;

        private int count;

    }




}
