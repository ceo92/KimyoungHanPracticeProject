package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    /**
     * 주문 취소
     */
    public void cancel() {
        //접근 시에는 무조건 getter로 !
        //주문 수량 원복해줌 , 상태 취소는 어차피 부모에서 다 해주니
        getItem().addStock(count);
    }

    /**
     * 주문에 대한 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    //주문상품을 생성해주는 메서드 아 그래서 생성메서드 ㅇㅇ
    // == 생성 메서드==//
    public static OrderItem createOrderItem(Item item , int orderPrice , int count){
        //orderPrice는 Item에 써있긴하지만 주문 시에는 고객이 갖고있는 할인쿠폰 같은거 적용될 수 있으니 ㅇㅇ
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }


}
