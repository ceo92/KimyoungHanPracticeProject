package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository= new MemoryMemberRepository();



    @AfterEach //테스트 케이스가 끝날 때마다 해당 코드 호출
    public void afterEach(){
        repository.clearStore();
    }


    @Test //해당 어노테이션 붙이면 해당 메서드만 테스트위해 실행됨
    public void save(){ //잘 저장이 되는지 확인
        Member member = new Member();
        member.setName("kyungMin"); //kyungMin 저장하며 member 객체 생성

        repository.save(member); //본 코드 save해보기
        Member result = repository.findById(member.getId()).get(); //본 코드에서 save 잘됐는지 알려면 id값이든 뭐든 가져와야됨

        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName(){
        Member member1 = new Member(); //actual 객체 생성
        member1.setName("member1"); //actual 객체 필드 생성
        repository.save(member1);

        Member member2 = new Member(); //actual 객체 생성
        member2.setName("member2"); //actual 객체 필드 생성
        repository.save(member2);


        Member result = repository.findByName(member1.getName()).get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("member1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("member2");
        repository.save(member2);

        List<Member> aa = repository.findAll();
        assertThat(aa.size()).isEqualTo(2);
    }


}
