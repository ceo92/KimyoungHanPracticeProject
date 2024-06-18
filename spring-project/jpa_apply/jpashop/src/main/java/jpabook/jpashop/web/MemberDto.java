package jpabook.jpashop.web;

import jakarta.validation.constraints.NotBlank;
import jpabook.jpashop.domain.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {

    @NotBlank
    private String name;
    //VO 검증은 우예 하노?

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String zipcode;

}
