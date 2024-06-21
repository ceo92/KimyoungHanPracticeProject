package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity //jpa가 관리되는 객체임을 알 수 있음
// @Table(name ="item")객체명이랑 매핑될 객체 이름을 같게 하려면 생략해도 됨
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)//애가 PK 구나 알 수 있음
    //DB에서 대체 키로 자동으로 값을 넣어주는 전략을 쓸거면 이거 써주면 됨
    private Long id;

    @Column(name = "item_name" , length = 10)
    private String itemName; //item_name으로 해당 칼럼이 매핑됨 , 크기도 지정가능
    private Integer price; //@Column 안 쓰면 매핑 안됨
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
