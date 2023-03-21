package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager(); // 엔티티 매니저를 꺼낸다 ex. DB 에 저장

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member();
        member.setId(1L);
        member.setName("hello");

        em.persist(member);

//        Member findMember = em.find(Member.class, 1L);
//        System.out.println("findMember.getId() = " + findMember.getId());
//        System.out.println("findMember.getName() = " + findMember.getName()); // select 쿼리가 나가지 않는다. -> 1차 캐시에서 조회 가능

        

        tx.commit();

        em.close();
        emf.close();
    }
}
