package hello.springtx.propagation;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final EntityManager em;
    //private final JPAQueryFactory query;



    @Transactional
    public Member save(Member member){
        em.persist(member);
        return member;
    }


    public Optional<Member> find(String username){
        String jpql = "select m from Member m where m.username=:username";
        Optional<Member> findMember = em.createQuery(jpql, Member.class)
                .setParameter("username" , username)
                .getResultList().stream().findAny();
        return findMember;
    }


}
