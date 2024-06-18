package hello.springtx.propagation;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@SpringBootTest
public class KyungMinTest {

    @Autowired External external;

    @Test
    void 외부에서내부호출체크(){
        external.outer();
    }


    @Slf4j
    static class External{
        private PlatformTransactionManager txManager;
        private Internal internal;

        @Autowired
        public External(PlatformTransactionManager txManager , Internal internal){
            this.txManager = txManager;
            this.internal = internal;
        }

        public void outer(){
            log.info("outer call");
            internal.inner();

        }

        @Transactional
        public void aa(){
            log.info("tx : aa call");
        }

        public void bb(){
            log.info("normal : bb call");
        }

        public void cc(){
            log.info("normal : cc call");
        }




    }

    @Slf4j
    static class Internal{
        PlatformTransactionManager txManager;
        @Autowired
        public Internal(PlatformTransactionManager txManager){
            this.txManager = txManager; //ㅋㅋ 이렇게 해야 빈으로 jdbc트랜잭션 매니저를 등록하고 해당객체를 여기다가 주입해서 쓰는 거지 new 를 여기서
            //객체를 여기서 생성하면 걍 빈으로 등록하는 게 아닌 여기서 객체 쓸 거야 하는 거잖아 ㅇㅇ
        }

        @Transactional
        public void inner(){
          log.info("inner call");
          //트랜잭션 검증 로직
        }

        public void innerAA(){
            log.info("normal : inner AA call");
        }

        public void innerBB(){
            log.info("normal : inner BB call");
        }
    }

    //설정 정보
    @TestConfiguration
    static class KyungminConfig{

        //@Autowired
        //private DataSource dataSource; //내 생각엔 어디에 두든 그냥 변수만 선언해주면 해당 인터페이스의 구현체를 변수에 어차피 스프링이 할당해주니 그거 쓰면될듯?

        //@Qualifier("fhgfg")
        @Bean
        public PlatformTransactionManager txManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public Internal internal(DataSource dataSource){
            return new Internal(txManager(dataSource));
        }

        @Bean
        public External external(DataSource dataSource){
            return new External(txManager(dataSource) , internal(dataSource));
        }
    }
}
