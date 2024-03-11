package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Parameter;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    //지원하는지 검증
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");
        //loginMember에 @Login 어노테이션이 붙어있는가?
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
        //member타입이고 , @Login어노테이션이 붙어있으면 true 반환 후 이 ArgumentResolver 구현체 실행 !
        return hasLoginAnnotation && hasMemberType;
    }

    //지원하면 해당 로직 실행해서 컨트롤러에 주입
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument");
        //webRequest를 ArgumentResolver은 받는데 거기서 getNativeRequest 하면 내가 원하는 request뽑을 수 있음
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        //요청 쿠키에서 세션이 존재하지 않으면 null
        if (session==null){
            return null;
        }

        //지정된 이름의 회원 객체 존재하면 그거 반환하고 없으면 null 반환
        return session.getAttribute(SessionConst.LOGIN_MEMBER);



    }
}
