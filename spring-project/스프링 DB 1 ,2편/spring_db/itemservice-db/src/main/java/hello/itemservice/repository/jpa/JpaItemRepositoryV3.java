package hello.itemservice.repository.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.QItem;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

import static hello.itemservice.domain.QItem.item;

@Repository
@Transactional
public class JpaItemRepositoryV3 implements ItemRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public JpaItemRepositoryV3(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = em.find(Item.class, itemId);
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    public List<Item> findAllOld(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(itemName)){
            builder.and(item.itemName.like("%" + itemName + "%"));
        }

        if (maxPrice != null){
            builder.and(item.price.loe(maxPrice));
        }


        //i가 QItem의 alias
        //QItem item = new QItem("i");

        List<Item> result = query.select(item)
                .from(item)
                .where(builder)
                .fetch();
        return result;//성공 시 true 실패 시 null 즉 그냥 조건만 사라짐 and 조건이라고 아예 말소되는 게 아닌 !
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        return query.select(item)
                .from(item)
                .where(likeItemName(itemName) , maxPrice(maxPrice)) //원래는 where 안에 item.itemName.like().and() 이런 식으로 짤 수 있는데 동적 쿼리이니 Builder을 넣은 것!
                //만약 likeItemName() 조건이 맞으면 해당 리턴이 적용되고 아니면 null 반환되니 where 조건이 무시가 됨!
                .fetch();
        //코드가 굉장히 간결 , return 문만 봐도 동적 쿼리 짤 수 있음
    }

    private Predicate maxPrice(Integer maxPrice) {
        if (maxPrice != null){
            return item.price.loe(maxPrice);
        }
        return null;
    }
    private BooleanExpression likeItemName(String itemName){
        if (StringUtils.hasText(itemName)){
            return item.itemName.like("%" + itemName + "%");
        }
        return null;
    }
}
