package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>  , MemberRepository {
    @Override
    Optional<Member> findByName(String name);


    //이렇게만 하면 끝


}
