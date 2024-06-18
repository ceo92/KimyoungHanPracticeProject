package jpql;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id @GeneratedValue
    private Long id;

    private int orderAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID") //이 member 필드가 ORDER_ID란 필드가 생성되며 거기에 매핑된,ㄴ 것?
    private Member member;
    //한 주문은 한 회원에게만 할당되므로 단일 객체로 생성


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product; //한 주문은 한 상품에게만 할당되니 , 또한 다대일 단방향은 다쪽에 외래키가 당연히 있어야하니 ㅇㅇ

    @Embedded
    private Address address;

    public Long getId() {
        return id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}
