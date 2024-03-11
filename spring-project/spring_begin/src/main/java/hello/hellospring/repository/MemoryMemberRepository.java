package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long , Member> store = new HashMap<>(); //이게 간이 DB 역할이네 ㅇㅇ save하면 해당 store 변수에 저장
    private static long sequence = 0L; //시스템 내부 기본키 id 주기

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //id는 시스템 내부에서 처리하니 여기서 함 , name은 외부에서 회원가입할때 사용
        store.put(member.getId() , member); //ID고유값과 member 객체 저장
        return member;

    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //null값 대비 감싸서 반환해주면 클라이언트에서 뭘 할수 있음
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().filter(a->a.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
