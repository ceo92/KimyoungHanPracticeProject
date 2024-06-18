package jpabook.jpashop.web;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/item/")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    @GetMapping("register")
    public String itemRegister(@ModelAttribute("item") ItemSaveDto itemSaveDto){
        return "item/register";
    }

    @PostMapping("register")
    public String postItemRegister(@Validated @ModelAttribute ItemSaveDto itemSaveDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "item/register";
        }
        Book book = new Book();
        book.setName(itemSaveDto.getName());
        book.setPrice(itemSaveDto.getPrice());
        book.setStockQuantity(itemSaveDto.getStockQuantity());
        book.setAuthor(itemSaveDto.getAuthor());
        book.setIsbn(itemSaveDto.getIsbn());
        itemService.saveItem(book);
        return "redirect:/";

    }

    @GetMapping("list")
    public String getList(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "item/list";

    }

    @GetMapping("{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        Book findItem = (Book) itemService.findOne(itemId);
        log.info("id0 = {}" , itemId);
        ItemUpdateDto itemUpdateDto = new ItemUpdateDto();
        itemUpdateDto.setId(findItem.getId());
        itemUpdateDto.setAuthor(findItem.getAuthor());
        itemUpdateDto.setIsbn(findItem.getIsbn());
        itemUpdateDto.setName(findItem.getName());
        itemUpdateDto.setPrice(findItem.getPrice());
        itemUpdateDto.setStockQuantity(findItem.getStockQuantity());
        log.info("id1 = {}" , itemUpdateDto.getId());
        model.addAttribute("item", itemUpdateDto);
        return "item/edit";
    }

    @PostMapping("{itemId}/edit")
    public String edit(@Validated @ModelAttribute("item") ItemUpdateDto itemUpdateDto , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "item/edit";
        }
        Book book = new Book();
        log.info("id2 = {}" , itemUpdateDto.getId());
        book.setId(itemUpdateDto.getId());
        book.setName(itemUpdateDto.getName());
        book.setPrice(itemUpdateDto.getPrice());
        book.setStockQuantity(itemUpdateDto.getStockQuantity());
        book.setAuthor(itemUpdateDto.getAuthor());
        book.setIsbn(itemUpdateDto.getIsbn());
        itemService.saveItem(book);
        return "redirect:/item/list";
    }




}
