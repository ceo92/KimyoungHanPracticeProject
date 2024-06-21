package happy.core.member;

import happy.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceImplTest {

    MemberService memberService;
    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }
    @Test
    void join() {
        //WHEN
        Member member = new Member(1L , Grade.VIP , "철수");
        //THEN
        memberService.join(member);
        //TEST
        Assertions.assertThat(member).isEqualTo(memberService.findMember(member.getId()));


    }
}