package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Team team1 = new Team();
            team1.setName("팀A");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("팀B");
            em.persist(team2);


            Member member1 = new Member();
            member1.setUsername("memberA");
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("memberB");
            member2.setTeam(team1);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("memberC");
            member3.setTeam(team2);
            em.persist(member3);

            int resultCount = em.createQuery("update Member m set m.age=20").executeUpdate();
            System.out.println("resultCount = " + resultCount);

            
            /*List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "memberA")
                    .getResultList();
            for (Member member : resultList) {
                System.out.println("member = " + member);

            }*/


            em.flush();
            em.clear();





            /*for (Member member : result) {
                System.out.println("member = " + member);
            }*/

            //List<Member> username = em.createNamedQuery("Member.findByUsername", Member.class).setParameter("username", member1.getUsername()).getResultList();


            /*List<Team> resultList = em.createQuery("select t from Team t inner join fetch t.members m", Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("result = "+resultList.size());
            for (Team team : resultList) {
                System.out.println("team.getName() = " + team.getName() + " , members = " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("member = " + member);
                    
                }
            }*/

            //Member와 Team의 공통분모 가져오는데 id가 1인 놈만 가져와 ㅇㅇ
            //TypedQuery : 타입이 정해진 쿼리, Query : 타입이 정해지지 않은 쿼리 : 일단 memberA 11  memberB 22 memberC 32 를 조회해와 그 뒤에 m.id = t.id가 아닌 놈을 없애

            tx.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
