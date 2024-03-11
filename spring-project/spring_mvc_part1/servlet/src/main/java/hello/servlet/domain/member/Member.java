package hello.servlet.domain.member;


import lombok.Getter;
import lombok.Setter;

@Getter  @Setter
public class Member {

    private Long id;
    private String username;
    private int age;


    public Member(){}

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    public Member(String username , int age) {
        this.username = username;
        this.age =age;
    }
}
