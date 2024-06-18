package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * 서비스는 단순 위임..? 굳이..?
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    //오직 위임

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId , Book param){
        Item findItem = itemRepository.findById(itemId).orElseThrow();
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());



    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }


    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow();
    }


}
