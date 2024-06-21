package happy.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long , Member> store = new HashMap<>();


    @Override
    public void save(Member member) {
        store.put(member.getId() , member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId); //id에 대응하는 member 값 가져오는 것
    }

}
