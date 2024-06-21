package jpabook.jpashop.repository;

import jpabook.jpashop.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

//그냥 DTO임
@Getter @Setter
public class OrderSearch {
    //회원 이름과 상태에 따라 검색이 가능하니 !
    private String memberName;
    private OrderStatus orderStatus;



}
