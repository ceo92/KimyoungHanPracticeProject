package jpql;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    private Long id;

    private String name;

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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
    //여러 회원은 한 팀에 소속되잖아



    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team" , fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();
    //한 팀은 여러 회원들에게 할당되므로 Team은 컬렉션 객체

}
