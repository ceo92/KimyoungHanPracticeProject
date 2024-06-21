package jpabook.jpashop.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSaveDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private int price;
    @NotNull
    private int stockQuantity;
    @NotBlank
    private String author;
    @NotBlank
    private String isbn;


}
