package jpql;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;



@NamedQuery(
        name = "Member.findByUsername", //관례적으로 엔티티명.쿼리명 이렇게 씀
        query = "select m from Member m where m.username = :username"

)
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String username;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    private int age;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    //한 회원이 여러 주문을 할 수 있으니 주문 리스트를 받아야됨

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team; //한 회원은 한 팀에만 소속되므로// Team은 단일 객체

    //Member : N , Team : 1 즉
    public void addTeam(Team team){
        this.team = team; //1 => 2 => 3 => 계속 초기화가 됐네
        team.getMembers().add(this); //
    }
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';

    }
}
