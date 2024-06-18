package hello.springtx.apply;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class TxBasicTest {
    @Autowired
    BasicService basicService;

    @Test
    void proxyCheck(){
        log.info("aop class={}" , basicService.getClass());
        Assertions.assertThat(AopUtils.isAopProxy(basicService)).isTrue();
        //해당 클래스가 등록될 때 클래스가 아닌 프록시가 등록됐는지?
      }

    @Test
    void txTest(){
        basicService.tx();
        basicService.nonTx();
    }

    @TestConfiguration
    static class TxApplyBasicConfig{
        @Bean
        public BasicService basicService(){
            return new BasicService();
        }
    }
    @Slf4j
    static class BasicService{

        @Transactional
        public void tx(){
            log.info("call tx");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            //트랜잭션 일어난지 확인하려면 트랜잭션 동기화 매니저의 쓰레드 로컬에 커넥션 동기화됐는지 확인하는데, 그거 있으면 true 없으면 false
            log.info("tx active={}" , txActive);
        }

        public void nonTx(){
            log.info("call nonTx");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}" , txActive);

        }

    }
}
