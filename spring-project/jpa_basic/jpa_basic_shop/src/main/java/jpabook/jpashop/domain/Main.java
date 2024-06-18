package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //해당 팩토리는 애플리케이션 로딩 시점에 딱 하나만 만들어야됨
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Delivery delivery = new Delivery();
            delivery.setAddress(new Address("old city" , "old street" , "old zipcode"));

            OrderItem orderItem = new OrderItem();
            orderItem.setCount(23);
            orderItem.setOrderPrice(10000);

            Order order = new Order();
            order.setStatus(OrderStatus.ORDER);
            order.addDelivery(delivery);
            order.addOrderItem(orderItem);
            em.persist(order);

            Member member = new Member();
            member.setName("adas");
            member.setHomeAddress(new Address("homeCity", "homeStreet", "homeZipcode"));
            member.setWorkAddress(new Address("workCity" , "workStreet" , "workZipcode"));
            order.addMember(member);
            em.persist(member);





            tx.commit(); //쿼리 날라가서 db에 수정된 결과 반영

        }catch (Exception e){
            tx.rollback();
        }finally {
            //사용 다 하면 EntityManager 닫음
            em.close();
        }
        //사용 끝나면 팩토리 까지 다 닫음
        emf.close();

        //스프링이 다 해주니 나중엔 이런 코드 없어짐
    }
}
