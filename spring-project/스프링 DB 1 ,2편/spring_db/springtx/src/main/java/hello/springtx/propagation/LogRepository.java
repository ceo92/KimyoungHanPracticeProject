package hello.springtx.propagation;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Repository
public class LogRepository {

    private final EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Log logMessage) { //로그 메시지를 저장 , 로그 예외라는 메시지 저장하면 오류
        log.info("log 저장");
        em.persist(logMessage);

        if (logMessage.getMessage().contains("로그예외")){
            log.info("log 저장 시 예외 발생");
            //런타임 예외이므로 롤백 ! , 시스템 에러이니 DB에 반영되면 큰일나니
            throw new RuntimeException("예외 발생");
        }
    }

    public Optional<Log> find(String message) {
        String jpql = "select l from Log l where l.message = :message";
        return em.createQuery(jpql , Log.class).setParameter("message" , message).getResultList().stream().findAny();
    }
}
