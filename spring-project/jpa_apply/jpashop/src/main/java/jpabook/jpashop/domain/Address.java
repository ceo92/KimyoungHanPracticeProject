package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

//값 타입 즉 임베디드 내장 타입이므로
@Embeddable //어딘가에 내장이 될 수 있다.
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;
    //값 타입은 변경이 되면 안 되므로 최초 생성 시에 값 할당 후엔 변경되지 않게 설계하자
    //즉 @Setter 쓰면 안됨!
    //
    protected Address(){}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
