package happy.core.common;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "request" , proxyMode = ScopedProxyMode.TARGET_CLASS) //request 스코프를 이용해서 예시와 같이 로그 찍힘
public class MyLogger {
    private String uuid;
    private String requestURL;


    public void setRequestURL(String requestURL){
        this.requestURL = requestURL;
    } //설정자 주입으로 나중에 중간에 url이 들어오게 세팅

    public void log(String message){
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);

    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString(); //글로벌한 유니크 아이디 생성
        System.out.println("[" + uuid + "] request scope bean create : " + this );

        //고객 요청 들어올 시 생성됨(초기화됨)
    }

    @PreDestroy
    public void close(){
        System.out.println("[" + uuid + "] request scope bean close : " + this );
        //고객 요청이 빠져나갈 때 호출
    }
}
