package hello.itemservice.item;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.*;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private static final Map<Long , Item> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(),  item);
        return item;
    }

    @Override
    public Item findById(Long id) {
        return store.get(id);

    }

    @Override
    public List<Item> findAll() {
        
        return new ArrayList<>(store.values());
    }

    @Override //왜 save는 Item을 반환했는데, update는 반환을 안하는 거지? 둘 다 서버에서 저장 혹은 수정을 유도하는 메서드들인데?
    public void update(Long itemId, Item updateParam) {
        Item updateItem = store.get(itemId);
        updateItem.setItemName(updateParam.getItemName());
        updateItem.setPrice(updateParam.getPrice());
        updateItem.setQuantity(updateParam.getQuantity());
    } //즉 클라이언트는 어떤 아이템을 어떤 식으로 수정해주겠다를 정의 new Item()로 어떻게 수정할지 정해주고 , 어떤 id에 해당되는 item을 바꿀지 지정한다.

    public void clearStore(){
        store.clear();
    }
}
