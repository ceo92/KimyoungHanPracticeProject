package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity {


    @Id @GeneratedValue
    private Long id;

    @Column(name = "TEAM_NAME")
    private String teamName;

    //Team 1 , Member N
    //add 할 때 NullPointer이 안 뜨니 이렇게 관례적으로 ArrayList로 초기화해서 씀

    @OneToMany(mappedBy = "team") //연관관계 주인 이름 team에 의해 매핑되어진다. 즉 조회만 됨 변경은 안되는
    private List<Member> members = new ArrayList<>();

    /*public void addMember(Member member){
        members.add(member);
        member.setTeam(this);
    }*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    protected Team() {
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                '}';
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
