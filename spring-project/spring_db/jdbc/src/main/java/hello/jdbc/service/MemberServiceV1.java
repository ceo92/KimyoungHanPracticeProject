package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {


    private final MemberRepositoryV1 memberRepositoryV1;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepositoryV1.findById(fromId); //출금되는 회원 객체 반환
        Member toMember = memberRepositoryV1.findById(toId); //입금받을 회원 객체 반환

        memberRepositoryV1.updateMember(fromId, fromMember.getMoney() - money); //출금되는 회원 객체에서 money만큼 뺌
        validation(toMember); //첫번째 감소로직에서 문제 생기면 두번째로 못넘어가짐
        memberRepositoryV1.updateMember(toId , toMember.getMoney() + money); //입금되는 회원 객체에서 money만큼 더함

    }

    private static void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
