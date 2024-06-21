package hello.login.web.session;


import hello.login.domain.member.Member;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest(){
        /**
         * 세션 생성
         */
        Member member = new Member();
        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession(member , response);


        //요청에 응답 쿠키 저장 , 직접 해줘야지 주입해주는 게 없으니 ㅇㅇ
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());


        // 세션 조회
        Member findMember = (Member)sessionManager.getSession(request); //세션dp가져와 아니면 전부 null
        Assertions.assertThat(findMember).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();


    }

}
