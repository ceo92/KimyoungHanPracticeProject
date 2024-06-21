package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
        //영속성 컨텍스트(Persistence Context)에 member 객체 넣음
    }

    public Optional<Member> findById(Long memberId){
        return Optional.ofNullable(em.find(Member.class , memberId));
    }


    public List<Member> findAll(){
        String jpql = "select m from Member m";
        return em.createQuery(jpql , Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        String jpql = "select m from Member m where m.name = :name";
        //이름 기준 바인딩이니 ㅇㅇ 순서로 하면 무조건 오류남 JdbcTemplate에서 배웠지? NamedJdbcTemplate이었나 암튼 ㅇㅇ
        return em.createQuery(jpql , Member.class).setParameter("name" , name).getResultList();

    }

}
