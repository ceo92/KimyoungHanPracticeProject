package happy.core.member;

public interface MemberService{

    void join(Member member); //회원 가입
    Member findMember(Long memberId); //회원조회

    //회원 하나 찾아오는 거만 했네 ?? , 꼴이 왜 저런 식으로 되는지??


}
