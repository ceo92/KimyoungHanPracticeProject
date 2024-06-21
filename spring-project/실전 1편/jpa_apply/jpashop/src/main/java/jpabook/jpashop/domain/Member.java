package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    //한 회원이 여러 주문을 하므로 일대다이고 여러 주문을 가질 수 있으니 리스트를 가짐!
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
