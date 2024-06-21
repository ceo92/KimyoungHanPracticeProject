package hello.springtx.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RollbackTest {

    @Autowired
    RollbackService service;

    @Test
    void runtimeException(){
        Assertions.assertThatThrownBy(() -> service.unCheckedException()).isInstanceOf(RuntimeException.class);
    }
    @Test
    void checkedException(){
        Assertions.assertThatThrownBy(()->service.checkedException()).isInstanceOf(MyCheckedException.class);
    }

    @Test
    void rollbackFor(){
        Assertions.assertThatThrownBy(()->service.rollbackFor()).isInstanceOf(MyCheckedException.class);
    }



    @TestConfiguration
    static class RollbackTestConfig{
        @Bean
        public Repository repository(){
            return new Repository();
        }
        @Bean
        public RollbackService service(){
            return new RollbackService(repository());
        }

    }
    @Slf4j
    @Transactional
    @RequiredArgsConstructor
    static class RollbackService{

        private final Repository repository;

        @Transactional
        public void unCheckedException(){
            log.info("call runtimeException");
            repository.throwUnCheckedException();
        }


        //체크예외 발생 : 커밋
        @Transactional
        public void checkedException() throws MyCheckedException{
            log.info("call checkedException");
            repository.throwCheckedException();
        }

        @Transactional(rollbackFor = MyCheckedException.class)
        public void rollbackFor() throws MyCheckedException{
            log.info("call rollbackFor");
            repository.throwCheckedException();
        }



    }


    static class Repository{
        public void throwCheckedException() throws MyCheckedException{
            throw new MyCheckedException();
        }

        public void throwUnCheckedException(){
            throw new RuntimeException();
        }
    }

    static class MyCheckedException extends Exception{}
}
