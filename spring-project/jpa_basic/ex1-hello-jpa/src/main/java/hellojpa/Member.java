package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "name" , nullable = false)
    private String username;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;


    @ElementCollection // 해당 테이블과 연관관계 맺은 컬렉션 테이블 조회 시에는 지연 로딩됨 즉 일단 변수에는 프록시 객체 껍데기만 임시로 할당하고, 프록시 객체를 사용하는 시점에 로딩  , 즉 지연로딩 !
    @CollectionTable(name = "FAVORITE_FOODS",joinColumns = @JoinColumn(name = "MEMBER_ID"))
    //@Column(name = "FOOD_NAME") String 타입이면 값이 하나가 되므로 필드 하나 일부러 추가해준 것 , 테이블에는 최소 기본 키 하나와 일반 필드 하나 이렇게 있어야하므로 !
    private Set<String> favoriteFoods = new HashSet<>();


    /*
        @ElementCollection
        @CollectionTable(name = "ADDRESS_HISTORY" , joinColumns = @JoinColumn(name = "MEMBER_ID"))
        private List<Address> addressHistory = new ArrayList<>();
    */
    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();



    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL) //다대일 매핑할거야
    @JoinColumn(name = "TEAM_ID")// 지정된 외래키에 매핑 , 즉 지정된 칼럼을 해당 필드를 통해 조인할 거야
    private Team team;





    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }



    /*@OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<Address> addressHistory = new ArrayList<>();*/



    //Address는 칼럼명을 값 타입이므로 내부 값들로 쓰면 됨

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
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

    public Team getTeam() {
        return team;
    }

    /**
     * 연관관계 편의 메서드
     *
     */
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    /*public void changeLocker(Locker locker){
        this.locker = locker;
        locker.setMember(this);
    }*/

    public Member(){}

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team +
                '}';
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
