package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * 트랜잭션 - @Transactional AOP
  */
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberServiceV3_3 {

    private final MemberRepositoryV3 memberRepository;



    //성공하면 commit , 런타임예외 터지면 rollback 이 어노테이션 하나로 끝
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        bizLogic(toId, money, fromId);
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
