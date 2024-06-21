package hello.itemservice.repository.v2;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//스프링 데이터 jpa의 JpaRepository는 기본적인 CRUD는 다 제공되니 , where 조건이 붙은 동적쿼리만 작성해주면 됨 !
// 그 동적쿼리는 @Query() 혹은 쿼리 메서드를 통해 가능!
public interface ItemRepositoryV2 extends JpaRepository<Item, Long> {
    //기본적인 CRUD(단순 조회 , 저장 , 수정) 만 스프링 데이터 jpa로 구현할 것이니 여러 개 조회와 같은 동적 쿼리 작성은 querydsl 로 !
   /* List<Item> findByItemNameLike(String itemName);

    List<Item> findByPriceLessThanEqual(Integer price);

    List<Item> findByItemNameLikeAndPriceLessThanEqual(String itemName, Integer price);

    @Query("select i from Item i where i.itemName like :itemName and i.price <= :price ")
    List<Item> findItems(@Param("itemName") String itemName , @Param("price") Integer price);*/



}
