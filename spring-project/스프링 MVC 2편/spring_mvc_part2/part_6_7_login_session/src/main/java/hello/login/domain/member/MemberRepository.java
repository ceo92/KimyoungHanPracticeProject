package hello.login.domain.member;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;


@Slf4j
@Repository
public class MemberRepository {
    private static Long sequence = 0L;
    private static Map<Long, Member> store = new HashMap<>();

    public Member save(Member member){
        member.setId(++sequence);
        log.info("save : member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId){
        //비밀번호 찾을 때와 같은ㅇㅇ
        List<Member> members = findAll();
        Optional<Member> first = members.stream().filter(member -> member.getLoginId().equals(loginId)).findFirst();
        return first;
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }


}
