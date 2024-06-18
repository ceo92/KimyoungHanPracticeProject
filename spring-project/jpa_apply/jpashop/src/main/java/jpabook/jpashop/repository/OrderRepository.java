package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.status.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.order;

@Repository

public class OrderRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    @Autowired
    public OrderRepository(EntityManager em) {
        this.em =em;
        this.query = new JPAQueryFactory(em);
    }

    public void save(Order order){
        em.persist(order);

    }

    public Optional<Order> findById(Long orderId){
        return Optional.ofNullable(em.find(Order.class , orderId));
    }


    /**
     * 검색 조건 : 1. 순수 JPQL
     */
    //OrderSearch DTO를 통해 외부로부터 주입받는 검색 데이터를 통해 검색 !
    public List<Order> findAll(OrderSearch orderSearch) {
        //jpql => sql 변환되어 db에서 실행!
        if (StringUtils.hasText(orderSearch.getMemberName()) && orderSearch
                .getOrderStatus() != null) {
            return em.createQuery("select o from Order o inner join o.member m" +
                            "where m.name like :name" +
                            "and o.status = :status", Order.class)
                    .setParameter("name", orderSearch.getMemberName())
                    .setParameter("status", orderSearch.getOrderStatus())
                    .setMaxResults(1000)
                    .getResultList();
        }
        else if (StringUtils.hasText(orderSearch.getMemberName())){
            return em.createQuery("select o from Order o inner join o.member M where m.nane like :name" , Order.class)
                    .setParameter("name" , orderSearch.getMemberName())
                    .setMaxResults(1000)
                    .getResultList();
        }
        else if (orderSearch.getOrderStatus() != null){
            return em.createQuery("select o from Order o join o.member M where o.status = :status" , Order.class)
                    .setParameter("status" , orderSearch.getOrderStatus())
                    .setMaxResults(1000)
                    .getResultList();
        }
        else{
            return em.createQuery("select o from Order o join o.member M", Order.class)
                    .setMaxResults(1000)
                    .getResultList();
        }
        //:status 이렇게 하면 주입된 dto 파라메터에 대한 status를 나타냄!
    }

    /**
     * 2. 검색 조건 : JPA Criteria
     */
    /*public List<Order> findAllByCriteria(OrderSearch orderSearch){

    }*/

    /**
     * 3-1. 검색 조건 : Query dsl
     */
    public List<Order> findAllByQueryDslV1(OrderSearch orderSearch) {
        OrderStatus status = orderSearch.getOrderStatus();
        String name = orderSearch.getMemberName();

        BooleanBuilder builder = new BooleanBuilder();
        //지정된 회원이름에 대한 리스트 반환
        if (StringUtils.hasText(name)) {
            builder.and(member.name.like("%"+orderSearch.getMemberName()+"%"));
            //파라메터를 바로 바인딩하네
        }
        //지정된 상태에 대한 리스트 반환
        if(status != null){
            builder.and(order.status.eq(status));
        }
        return query.select(order)
                .from(order)
                .where(builder)
                .fetch();
    }

    /**
     * 3-2. 검색 조건 : Query dsl
     */
    public List<Order> findAllByQueryDslV2(OrderSearch orderSearch) {
        OrderStatus status = orderSearch.getOrderStatus();
        String name = orderSearch.getMemberName();

        return query.select(order)
                .from(order)
                .where(likeName(name), equalStatus(status)) //Predicate은 각각 리팩토링된 메서드들이 and 조건?
                .fetch();
    }

    private BooleanExpression likeName(String name){
        if (StringUtils.hasText(name)){
            return member.name.like("%" + name + "%");
        }
        return null; //null이면 포함 안하나?
    }



    private BooleanExpression equalStatus(OrderStatus status){
        if (status != null){
            return order.status.eq(status); //주문한 Order객체의 상태 정보가 주입받은 상태 정보와 같은지?
        }
        return null;
    }





    //어차피 조건에 대한 검색을 하므로 전체 조회는필요없음 , 검색조건없으면 당연 전체조회도 되니 ㅇㅇ
    /*public List<Order> findAll(){
        String jpql = "select o from Order o";
        return em.createQuery(jpql , Order.class).getResultList();
    }*/


}
