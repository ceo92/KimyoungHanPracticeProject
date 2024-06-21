package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원가입(){
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long joinId = memberService.join(member);

        //then
        Assertions.assertThat(member).isEqualTo(memberService.findOne(joinId));

    }

    @Test
    void 중복회원테스트(){
        //given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("memberA");
        member2.setName("memberA");

        //when
        memberService.join(member1);

        //then
        Assertions.assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(IllegalStateException.class).hasMessage("이미 존재하는 회원입니다!");
    }

}