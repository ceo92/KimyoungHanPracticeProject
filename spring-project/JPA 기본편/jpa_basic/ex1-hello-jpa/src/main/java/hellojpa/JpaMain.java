package hellojpa;

import jakarta.persistence.*;
import jakarta.transaction.TransactionManager;
import org.hibernate.Hibernate;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.Lock;


public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //해당 팩토리는 애플리케이션 로딩 시점에 딱 하나만 만들어야됨
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("Asdasd");
            em.persist(member);

            List<Member> resultList = em.createQuery("select m from Member m where m.username like '%kim%'", Member.class).getResultList();
            em.createNativeQuery("select * from Member where name like '%kim%'" , Member.class).getResultList();


            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            //사용 다 하면 EntityManager 닫음
            em.close();
        }
        //사용 끝나면 팩토리 까지 다 닫음
        emf.close();

        //스프링이 다 해주니 나중엔 이런 코드 없어짐
    }
}
