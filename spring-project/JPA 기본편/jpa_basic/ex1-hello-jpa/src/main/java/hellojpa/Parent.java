package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }

    @Id @GeneratedValue
    private Long id;

    private String name;

    //고아도 테스트 해보자
    @OneToMany(mappedBy = "parent"  , cascade = CascadeType.REMOVE)
    private List<Child> childList = new ArrayList<>();


    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
