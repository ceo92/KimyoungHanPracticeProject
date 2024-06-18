package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
  */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV3_1 {
    //private final DataSource dataSource; 이거에 직접 의존하니 JDBC에 직접 의존하는 꼴 , ㅇㅇ;; 다른 JPA로 바뀌면 바꿔줘야하니
    private final MemberRepositoryV3 memberRepository;
    private final PlatformTransactionManager transactionManager;
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());//일단 디폴트로 둠 나중에 트랜잭션 속성에 대해 공부 ㄱ
        try{
            //비즈니스 로직 수행
            bizLogic(toId, money, fromId);
            transactionManager.commit(status); //정상 수행 시 커밋

        }catch (Exception e){
            transactionManager.rollback(status); //validation으로 검증한 부분을 여기서 잡네
            throw new IllegalStateException(e);
        }




    }

    private void bizLogic(String toId, int money, String fromId) throws SQLException {
        Member fromMember = memberRepository.findById(fromId); //출금되는 회원 객체 반환
        Member toMember = memberRepository.findById(toId); //입금받을 회원 객체 반환

        memberRepository.updateMember(fromId, fromMember.getMoney() - money); //출금되는 회원 객체에서 money만큼 뺌
        validation(toMember); //첫번째 감소로직에서 문제 생기면 두번째로 못넘어가짐
        memberRepository.updateMember(toId, toMember.getMoney() + money); //입금되는 회원 객체에서 money만큼 더함
    }



    private static void validation(Member toMember) throws SQLException {
        if (toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
