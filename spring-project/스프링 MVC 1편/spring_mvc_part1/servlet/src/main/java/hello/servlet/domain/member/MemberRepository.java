package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MemberRepository {
    private static Map<Long , Member> store = new HashMap<>(); //메모리
    private static Long sequence = 0L; // DB에 저장할 식별자 , 아 이 식별자는 애초에 Member랑은 아예 상관 없구나

    //싱글톤 로직
    private static final MemberRepository instance = new MemberRepository();
    public static MemberRepository getInstance(){
        return instance;
    }
    private MemberRepository(){}



    //비즈니스 로직(저장,  반환)
    public Member save(Member member){
        member.setId(++sequence);//각각의 데이터를 저장하는게 아닌 객체를 만들어서 그 객체로 데이터를 저장하는거네
        store.put(member.getId(), member);
        return member; //원래 스펙이 이런건가?
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values()); //저장소에 있는 값들 ArrayList로 받아서 반환
        //store을 보호하기 위해서
    }
    public void clearStore(){
        store.clear();
    }

}
