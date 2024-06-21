package hello.itemservice.repository.v2;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static hello.itemservice.domain.QItem.item;

@Slf4j
@Repository
public class ItemQueryRepositoryV2 {
    //리포지토리는 Querydsl 쪽이 되고, 단순 crud는 그냥 서비스에서 바로 스프링 데이터 jpa를 통해 사용 ! , 스프링 데이터 jpa 리포지토리는 특정 계층에 속하지 않은 그저 사용되는 도구 즉 기술에 불과함 !
    private final JPAQueryFactory query;

    public ItemQueryRepositoryV2(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Item> findAllOld(ItemSearchCond cond){
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        //Querydsl => Q객체를 통한 쿼리 작성 =>
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(itemName)){
            builder.and(item.itemName.like(itemName)); //like와 같은 자세한 쿼리는 여기서 결정
        }
        if (maxPrice != null){
            builder.and(item.price.loe(maxPrice));
        }
        //fetch()
        return query.select(item).from(item).where(builder).fetch();
    }

    public List<Item> findAll(ItemSearchCond cond){
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();
        //Querydsl => Q객체를 통한 쿼리 작성 =>

        //fetch()
        return query
                .select(item)
                .from(item).where(itemNameLike(itemName) , maxPriceLessThanEqual(maxPrice)).fetch();
    }


    private BooleanExpression itemNameLike(String itemName) {
        if (StringUtils.hasText(itemName)){
            return item.itemName.like(itemName);
        }
        return null;
    }
    private BooleanExpression maxPriceLessThanEqual(Integer maxPrice) {
        if (maxPrice != null){
            return item.price.loe(maxPrice);
        }
        return null;
    }

}
