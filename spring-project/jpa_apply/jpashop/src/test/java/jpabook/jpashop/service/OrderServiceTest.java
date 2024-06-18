package jpabook.jpashop.service;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.status.DeliveryStatus;
import jpabook.jpashop.status.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;
    @Test
    void 주문(){
        //given
        Member member = createMember("kim", new Address("Seoul", "도산대로", "1001"));

        Item item = createItem("시골 JPA", 15000, 20);
        int orderCount=10;


        //when
        //orderService로의 주문은 무조건 주문을 한 것이므로 ready가 됨?
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        //주문에 의한 각각의 케이스들 전부 테스트!
        Order order = orderRepository.findById(orderId).orElseThrow();
        Assertions.assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        Assertions.assertThat(order.getOrderItems().size()).isEqualTo(1);
        Assertions.assertThat(order.getTotalPrice()).isEqualTo(15000*10);
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    @Test
    void 주문_수량_초과(){
        //given
        //회원 생성
        Member member = createMember("kim", new Address("Seoul", "도산대로", "1001"));
        //상품 생성
        Item item = createItem("시골 JPA", 15000, 20);

        int orderCount = 21;
        //주문 상품 생성

        Assertions.assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount)).isInstanceOf(NotEnoughStockException.class).hasMessage("need more stock");


        //then

    }

    @Test
    void 주문_취소(){
        //given
        Member member = createMember("이갱", new Address("Seoul", "도산대로", "aa11"));
        Item item = createItem("쿠크다스", 9000, 20);
        int orderCount = 15;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);
        Order order = orderRepository.findById(orderId).orElseThrow();
        //then
        Assertions.assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(20);

        //DeliveryStatus.COMP이면 주문 취소 불가능
        //DeliveryStatus.READY이면 주문 취소

    }

    //Item과 Member은 다른 엔티티에 의해 CasecadeType속성이 없으니 직접 jpa에 persist()해주면서 저장해줘야됨!
    private Item createItem(String name, int price, int stockQuantity) {
        Item item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        em.persist(item);
        return item;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }

}