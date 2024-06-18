package hello.jdbc.repository;

import hello.jdbc.domain.Member;

public interface MemberRepository {

    //CRUD
    Member save(Member member);

    Member findById(String memberId);

    void updateMember(String memberId, int money); //update memberId 위치에 있는 money를 수정할 것이니

    void deleteMember(String memberId);


}
