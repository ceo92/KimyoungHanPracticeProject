package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.status.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    /**
     * 주문
     */
    //주문을 하려면
    @Transactional//조회가 아니므로 readOnly=false
    public Long order(Long memberId , Long itemId , int count){
        //엔티티 조회
        Member member = memberRepository.findById(memberId).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(delivery.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문상품 생성
        //Order과 OrderItem은 new로 생성하지 않고 생성 메서드를 통해서 생성하네 ㅇㅇ
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        //Order만 persist 하면 cascade all을 통해 Delivery 및 Member 도 연달아 persist 됨
        //그래서 Order만 저장 jpa 해주고 나머지는 안 해준 것!
        orderRepository.save(order);
        return order.getId();

    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.cancel();
    }
    //조회(검색)
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
}
