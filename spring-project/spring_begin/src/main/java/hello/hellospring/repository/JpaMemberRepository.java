package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;


public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);  //영구 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class , id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        return em.createQuery("select m.name from Member m where m.name=:name",Member.class)
                .setParameter("name" , name).getResultList().stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m ", Member.class).getResultList();
        //Member이라는 객체 자체를 select 하는 것 sql에선 *하거나 m.id , m.name이랬는데 이건 그냥 객체 통째로 선택하는 것 , 이미 매핑도 도메인에서 다 해줬기때문
        //알아서 매핑된 또아리로 묶여서 객체 형태로 다 가져옴

    }
}
