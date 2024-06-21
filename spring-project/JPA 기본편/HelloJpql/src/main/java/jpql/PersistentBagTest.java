package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;

public class PersistentBagTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Team team = new Team();
            System.out.println("컬렉션 비영속 상태 : " + team.getMembers().getClass().getName());
            em.persist(team);

            Member member = new Member();
            member.setUsername("abcd");
            member.addTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            System.out.println(findTeam.getMembers().get(0).getUsername());
            System.out.println("컬렉션 영속 상태 : " + findTeam.getMembers().getClass().getName());


     //       System.out.println("컬렉션 영속 상태 : "+team.getClass().getName());

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();

        }finally {
            em.close();
        }
        emf.close();

    }
}
