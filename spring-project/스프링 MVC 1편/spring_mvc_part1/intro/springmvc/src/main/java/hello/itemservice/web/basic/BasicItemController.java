package hello.itemservice.web.basic;


import hello.itemservice.item.Item;
import hello.itemservice.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;
    //Get 방식으로 오면 이 메서드가 호출됨 => 해당 컨트롤러와 뷰 연결

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items" , items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item" , item);
        return "basic/item";
        //왜 포장 클래스 오류?
    }


    //이건 단순 등록 폼 화면을 보여주기 위한 용도이니 그냥 뷰랑 연결만 지으면 됨 실제 저장 알고리즘은 Post에서 ㅇㅇ 하나씩 하자
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }


    //@PostMapping("/add") //등록 폼에서 등록을 누르면 Post방식이니 현재 add url에서 현재 로직이 수행됨
    public String addItemV1(@RequestParam("itemName") String itemName , @RequestParam("price") int price
            , @RequestParam("quantity") int quantity , Model model){
        Item item = new Item(itemName , price , quantity);
        itemRepository.save(item);
        model.addAttribute("item" , item);
        return "basic/item"; //여기에는 뷰 템플릿 위치 써주는 거잖아 ㅇㅇ 그니까 제품 상세를 보기 위해 basic/item 써준 것임
        // 상품 저장하고 나면 상품 상세 화면에서 저장된 결과를 보여주도록 로직 구성할 것임

    }

    //@PostMapping("/add") //등록 폼에서 등록을 누르면 Post방식이니 현재 add url에서 현재 로직이 수행됨
    public String addItemV2(@ModelAttribute Item item , Model model){
        itemRepository.save(item);
        model.addAttribute("item" , item);
        return "basic/item";
    }
    //같은 URL인데 HTTP 메서드로 기능을 구분해줌

    //@PostMapping("/add") //등록 폼에서 등록을 누르면 Post방식이니 현재 add url에서 현재 로직이 수행됨
    public String addItemV3(@ModelAttribute("item") Item item){
        itemRepository.save(item);
        return "basic/item";

    }

    //@PostMapping("/add") //등록 폼에서 등록을 누르면 Post방식이니 현재 add url에서 현재 로직이 수행됨
    public String addItemV4(Item item){
        itemRepository.save(item);
        return "basic/item";

    }

    //@PostMapping("/add") //등록 폼에서 등록을 누르면 Post방식이니 현재 add url에서 현재 로직이 수행됨
    public String addItemV5(Item item){
        itemRepository.save(item);
        return String.format("redirect:/basic/items/%s" , item.getId());
    }
    @PostMapping("/add") //등록 폼에서 등록을 누르면 Post방식이니 현재 add url에서 현재 로직이 수행됨
    public String addItemV6(Item item , RedirectAttributes redirectAttributes){
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId" , saveItem.getId());
        redirectAttributes.addAttribute("status" , true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId , Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item" , item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId ,@ModelAttribute("item") Item item){
        itemRepository.update(itemId , item);
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA" , 10000 , 10));
        itemRepository.save(new Item("itemB" , 20000 , 15));
    }


}
