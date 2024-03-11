package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {
    MemberRepository memberRepository = MemberRepository.getInstance();
    //스프링 안 써서 싱글톤 쓰는 것
    @AfterEach
    void afterEach(){
        //테스트 끝나고 나면 비워줘야 됨 테스트 끝나자마자 자리 치워줘야됨 다음 테스트에 야예 영향 안 미치게
        memberRepository.clearStore();
    }

    @Test
    void save() {
        Member member = new Member();
        member.setUsername("Amy");
        member.setAge(24);
        Member result = memberRepository.save(member);//save되면서 DB에는 식별자 ID 가 1이 저장됨
        Assertions.assertThat(member).isEqualTo(result);
        System.out.println("member = " + member);
        System.out.println("result = " + result);
        //System.out.println("memberRepository = " + memberRepository.findById(1L));

    }

    @Test
    void findById() {
        Member member = new Member("Amy", 24); //actual
        memberRepository.save(member);
        Member result = memberRepository.findById(member.getId());
        Assertions.assertThat(member).isEqualTo(result);
        System.out.println("member = " + member);
        System.out.println("result = " + result);
        //System.out.println("memberRepository = " + memberRepository.findById(2L));

    }

    @Test
    void findAll() {
        Member member1 = new Member("ko",2);
        Member member2 = new Member("ff" , 10);
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> members = memberRepository.findAll();
        Assertions.assertThat(members.size()).isEqualTo(2);
    }
    //그냥 스프링을 안 쓰니까 굳이 싱글톤으로 리포지토리를 구현해준 거구나 스프링처럼 하게 할라고 , 싱글톤이든 아니든 store메모리에선 테스트 종료 시 메모리 내려가니 사라짐 데이터



    //테스트를 전부 돌리게 되면 멤버 리포지토리 객체는 싱글톤이기 때문에 저장소에 다른 테스트에서 사용했던 데이터가 남게됨 따라서
    //따라서 데이터를 깔끔하게 없애주는 작업이 필요함 , 테스트는 독립적이어야 하므로!
}