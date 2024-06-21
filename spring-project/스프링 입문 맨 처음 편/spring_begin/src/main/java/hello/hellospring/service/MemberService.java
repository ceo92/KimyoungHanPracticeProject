package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {



    private final MemberRepository memberRepository; //인터페이스 변수?
    //목적 : 하나의 저장소 접근 객체를 이용하기 위해서
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public Long join(Member member){
        //Rule1 : 같은 이름이 있는 중복 회원 X
        dedupe(member);
        memberRepository.save(member); //중복이 없으면 이름 저장
        return member.getId(); // id만 반환해주겠다고 스펙을 임의로 설정
    }

    /**
     * 전체 회원 조회
     */
    private void dedupe(Member member) {
        memberRepository.findByName(member.getName()) //사용자가 입력한 name과 저장소에 아예 없는 경우(null일 경우) 문장실행 안하고 있을 경우 예외 실행
                .ifPresent(a-> {
            throw new IllegalStateException("It's already exists");
    });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll(); //모든 멤버 가져오니 저장소에 있는 모든 데이터 가져오기
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId); //하나만 가져오기 고유값인 ID만 입력할 경우 하나의 회원 정보만 가져오게됨
    }


}
