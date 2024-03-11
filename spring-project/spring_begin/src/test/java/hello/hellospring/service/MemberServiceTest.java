package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;


    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }



    @Test
    public void join() { //정상 플로우

        //given , 뭔가가 주어졌는데 , 생성 코드
        Member member = new Member();
        member.setName("hello");

        //when 이걸 실행했는데 , 검증부
        Long saveId = memberService.join(member);
        //then 결과가 이게 나와야됨
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복회원(){ //예외 플로우
        //expect
        Member member1 = new Member();
        member1.setName("hello");

        Member member2 = new Member();
        member2.setName("hello");


        //actual
        memberService.join(member1);
        IllegalStateException e1 = Assertions.
                assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e1.getMessage()).isEqualTo("It's already exists");
        //람다 로직을 실행할 건데 해당 예외가 터져야함

        /*try{
            memberService.join(member2);
            fail("fail");
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("It's already exists123");
        }*/

        //test

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}