package hellojpa;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class ValueMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{

            /*Team team = new Team();
            team.setTeamName("asdsad");
            em.persist(team);

            Member member = new Member();
            member.setUsername("갱민");
            member.changeTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.getReference(Member.class, member.getId()); //Member 프록시 ㅎ ㅏㄹ당
            System.out.println("===========");
            findMember.getTeam().getTeamName(); //이때 프록시가 실제  Member의 Team 조회 후
            //findMember.getTeam() : 일단 Member 프록시가 초기화 그런데 Team 연관관계 객체가 지연로딩으로 되어있으니 실제 Member 객체에는 프록시 Team 객체 할당
            //findMember.getTeam().getTeamName() :
            System.out.println("===========");
            em.remove(findMember);*/

/*
            Member member = new Member();
            member.setUsername("ㅇㅇㅇ");
            em.persist(member); //이 순간 Member에 id 할당됨 ㅇㅇ

            em.flush();
            em.clear();//영속성 컨텍스트 비워

            Member reference = em.getReference(Member.class, member.getId());//프록시 객체 반환

            Team team = new Team();
            team.setTeamName("asd");
            team.setCreatedBy("asd");
            System.out.println("=========================");

            em.persist(team);
            reference.changeTeam(team); //프록시를 통해 실제 객체에 update 쿼리 날리는 거네 ㅇㅇ
            System.out.println("=========================");
            //프록시 객체 초기화 안 하고 삭제 시 지연 로딩 걸려있던 값 타입 컬렉션도 덩달아 삭제됨
            //영속성 컨텍스트에 아무것도 없는 상태에서 프록시 객체 조회 후 초기화하지 않은 상태에서 삭제하면
            */

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }


}
