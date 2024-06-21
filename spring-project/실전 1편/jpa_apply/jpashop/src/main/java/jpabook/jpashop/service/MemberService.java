package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    // id라도 반환해야 뭐가 저장됐는지 알 수 있음 ㅇㅇ
    @Transactional
    public Long join(Member member){
        validatedDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validatedDuplicateMember(Member member) {
        Member findMember = memberRepository.findByName(member.getName()).stream().findFirst().orElse(null);
        if (findMember != null){
            throw new IllegalStateException("이미 존재하는 회원입니다!");
        }

    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 한명 조회
    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).orElseThrow();
    }

}
