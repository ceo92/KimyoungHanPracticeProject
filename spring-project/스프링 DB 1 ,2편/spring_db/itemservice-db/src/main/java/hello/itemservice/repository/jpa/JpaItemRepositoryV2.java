package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
@RequiredArgsConstructor
public class JpaItemRepositoryV2 implements ItemRepository {

    //리포지토리에서 당연히 springdatajpa 리포지토리를 di 받아야되지 서비스는 순수하게 유지해야하니 !
    private final SpringDataJpaItemRepository repository;


    @Override
    public Item save(Item item) {
        return repository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = repository.findById(itemId).orElseThrow();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        // 둘 다 값이 있는 경우
        if (StringUtils.hasText(itemName) && maxPrice != null) {
            return repository.findItems("%"+itemName+"%", maxPrice);
        }
        //itemName만 있는 경우
        else if (StringUtils.hasText(itemName)){
            return repository.findByItemNameLike("%"+itemName+"%");
        }
        else if (maxPrice != null){
            return repository.findByPriceLessThanEqual(maxPrice);
        }
        else{
            return repository.findAll();
        }

    }
}
