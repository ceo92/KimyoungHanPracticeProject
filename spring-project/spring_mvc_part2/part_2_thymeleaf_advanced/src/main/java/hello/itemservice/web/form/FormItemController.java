package hello.itemservice.web.form;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String , String> regions(){
        Map<String , String> regions = new LinkedHashMap<>();
        regions.put("SEOUL" , "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }
    //해당 컨트롤러 내부 어떤 요청이든 호출될때 이 객체가 모델에 담기게 됨 , regions라는 이름으로 !
    //regions 나타내기 위함임

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){
        ItemType[] values = ItemType.values(); //values() 하면 enum 내의 값들 배열로 넘겨줌
        return values;
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes(){
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items); //회원 목록 페이지 보여줌
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item" , new Item());
        return "form/addForm";
        //GET으로 조회할 때는 클라이언트는 Item에 대한 아무런 정보가 없으니 당연히 Item객체를 갖고 뭘 할 수가 업승ㅁ
        //뷰에서 POST 작업 이후에 @PostMapping된 컨트롤러로 Item객체를 만들어서 값을 집어넣어줌
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        log.info("item.getOpen() = {}" , item.getOpen());
        log.info("regions() = {}",item.getRegions()); //Item 객체에 regions 리스트 변수에 체크된 SEOUL , BUSAN , JEJU값의 리스트를 할당함
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId()); //th:field로 지정한 값 여기서 할당하게됨
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }






}

