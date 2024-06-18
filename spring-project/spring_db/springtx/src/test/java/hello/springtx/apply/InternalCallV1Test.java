package hello.springtx.apply;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Call;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {

    @Autowired
    CallService service; //CallService는 트랜잭션 aop 프록시가 적용이 됨 , 내부 메서드에 붙어있으니 !

    @Test
    void printProxy(){
        log.info("callService class={}" ,service.getClass() );
    }
    @Test
    void externalCallV2(){
        service.external();
    }

    @TestConfiguration
    static class InternalCallV1TestConfig{

        @Bean
        public CallService callService(){
            return new CallService(internalService());
        }
        @Bean
        public InternalService internalService(){
            return new InternalService();
        }
    }

    @Slf4j
    static class CallService{

        private final InternalService internalService;

        @Autowired
        CallService(InternalService internalService) {
            this.internalService = internalService;
        }

        public void external(){
            log.info("call external");
            printTxInfo();
            internalService.internal();
        }



        private void printTxInfo(){
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive(); //트랜잭션이 적용됐는지?
            log.info("tx active={}", txActive);

        }
    }

    static class InternalService{
        @Transactional
        public void internal(){
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo(){
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive(); //트랜잭션이 적용됐는지?
            log.info("tx active={}", txActive);

        }
    }


}
