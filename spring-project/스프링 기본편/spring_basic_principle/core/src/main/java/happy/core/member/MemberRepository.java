package happy.core.member;

import happy.core.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void save(Member member);
    Member findById(Long memberId);
    /*Optional<Member> findName();
    Optional<Member> findGrade();
    List<Member> findAll();*/
    // 그냥 간단하게 하기 위해 이 정도 api만 구현한 것


}
