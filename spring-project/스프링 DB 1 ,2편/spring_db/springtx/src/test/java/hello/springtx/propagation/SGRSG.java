package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;

@SpringBootTest
@Slf4j
public class SGRSG {

    @Autowired private AA aa;
    @Autowired private BB bb;
    @Autowired private PlatformTransactionManager txManager;


    @Test
    void aa(){
        aa.aa();
    }
    @TestConfiguration
    @RequiredArgsConstructor
    static class Config{

        private final DataSource dataSource;

        @Bean
        public PlatformTransactionManager aa(){
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public AA aaaa(){
            return new AA(bbbb());
        }

        @Bean
        public BB bbbb(){
            return new BB();
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    static class AA{
        private final BB bb;
        @Autowired private PlatformTransactionManager txManager;

        @Transactional
        public void aa(){
            log.info("aa start");
            TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());
            log.info("aa another tx status = {}" , status);
            bb.bb();
        }
    }

    @Slf4j
    static class BB{
        @Autowired private PlatformTransactionManager txManager;

        @Transactional
        public void bb(){
            log.info("bb start");
            TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());
            log.info("bb another tx status = {}" , status);
        }
    }

}
