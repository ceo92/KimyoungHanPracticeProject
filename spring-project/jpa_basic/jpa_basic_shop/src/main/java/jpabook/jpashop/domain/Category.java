package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;


    @OneToMany(mappedBy = "category" , fetch = FetchType.LAZY)
    private List<CategoryItem> categoryItems = new ArrayList<>();


    private String name;

    /**
     * 셀프 양방향 매핑?
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent" , fetch = FetchType.LAZY)
    private List<Category> child = new ArrayList<>();

}
