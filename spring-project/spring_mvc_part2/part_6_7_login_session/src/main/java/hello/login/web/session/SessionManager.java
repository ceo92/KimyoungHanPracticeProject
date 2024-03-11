package hello.login.web.session;


import hello.login.domain.member.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    Map<String, Object> sessionStore = new ConcurrentHashMap<>();
    public static final String SESSION_COOKIE_NAME = "mySessionId";

    /**
     * 세션 생성
     */
    public void createSession(Member member , HttpServletResponse response){
        String sessionId = UUID.randomUUID().toString(); //세션 id 생성
        sessionStore.put(sessionId, member); //key : 세션id , 값
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);

    }

    /**
     *  세션 조회
     */
    public Object getSession(HttpServletRequest request){
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME); //요청 쿠키 조회
        if (cookie == null){
            return null;
        }
        return sessionStore.get(cookie.getValue()); //요청 쿠키에서 온 세션 id와 함께 매핑된 사용자 정보 반환
    } //이걸로 요청받은 쿠키 정보를 통해 세션 저장소를 뒤져서 "실제 사용자 정보"에 접근할 수 있다 !


    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request){
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME); //요청 쿠키 조회
        if (cookie != null){
            sessionStore.remove(cookie.getValue()); //요청 쿠키에 해당되는 세션 정보 삭제
        }
    }

    public Cookie findCookie(HttpServletRequest request , String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null; //요청에서 쿠키가 하나도 없다면 null 반환
        }
        return Arrays.stream(cookies).filter(c -> c.getName().equals(cookieName)).findFirst().orElse(null);
        //배
    }
}
