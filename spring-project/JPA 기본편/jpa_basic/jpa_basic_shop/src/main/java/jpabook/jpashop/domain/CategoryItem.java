package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORY_ITEM")
public class CategoryItem extends BaseEntity {


    @Id @GeneratedValue
    @Column(name = "CATEGORY_ITEM_ID")
    private Long id;
    @OneToMany(mappedBy = "categoryItem" , fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;




}
