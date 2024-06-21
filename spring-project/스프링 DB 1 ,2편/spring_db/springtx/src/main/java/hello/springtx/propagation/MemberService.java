package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    @Transactional
    public void joinV1(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        //트랜잭션을 각각 사용하는 예제
        log.info("== memberRepository 호출 시작 ==");
        memberRepository.save(member);
        log.info("== memberRepository 호출 종료 ==");

        log.info("== logRepository 호출 시작 ==");
        logRepository.save(logMessage);
        log.info("== logRepository 호출 종료 ==");

    }

    @Transactional
    public void joinV2(String username){
        Member member = new Member(username);
        Log logMessage = new Log(username);

        //트랜잭션을 각각 사용하는 예제
        log.info("== memberRepository 호출 시작 ==");
        memberRepository.save(member);
        log.info("== memberRepository 호출 종료 ==");

        log.info("== logRepository 호출 시작 ==");
        //로그에서 예외 터지면 잡을 거야
        try {
            logRepository.save(logMessage);
        }catch (RuntimeException e){
            log.info("log 저장에 실패했습니다. logMessage={}" , logMessage.getMessage());
            log.info("정상 흐름 반환");
        }
        log.info("== logRepository 호출 종료 ==");

    }

    //V1 : RuntimeEXCEPTION 밖에 나가가지고 고객에게 예외 화면 송출 , V2 : 예외를 시스템에서 잡아서 고객에게 보이지 않게 ! , 로그가지고 굳이 가입에 문제 생기게 해야되나 하는 마인드 ㅇ

}
