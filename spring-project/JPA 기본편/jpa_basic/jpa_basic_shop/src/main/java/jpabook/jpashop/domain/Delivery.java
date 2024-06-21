package jpabook.jpashop.domain;

import jakarta.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
//단방향 조회하는 쪽에 외래키 => 외래키 있는 곳에 참조 객체 둘래 아니면 없는 곳에 둘래?
    // => 있는 곳에 둬야 편함 , 없는 곳ㅇ

    @OneToOne(mappedBy = "delivery" , fetch = FetchType.LAZY)
    private Order order;
    @Embedded
    private Address address;

    private DeliveryStatus status;
}
