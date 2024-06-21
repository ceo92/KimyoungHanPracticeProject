package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;
    //저장 및 수정 동시에?
    public void save(Item item) {
        //아이디 없으면 신규저장
        if (item.getId()== null){
            em.persist(item);
        }
        //아이디 있으면 강제로 수정
        else{
            em.merge(item);
            //업데이트
        }
    }

    public Optional<Item> findById(Long itemId){
        return Optional.ofNullable(em.find(Item.class , itemId));
    }

    public List<Item> findAll(){
        String jpql = "select i from Item i";
        return em.createQuery(jpql , Item.class).getResultList();
    }


}
