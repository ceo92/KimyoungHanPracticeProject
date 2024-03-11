
package happy.core.web;

import happy.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HttpServletBean;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo") //log-demo라는 요청이 오면
    @ResponseBody
    public String logDemo(HttpServletRequest request){ //사용자 요청 정보 request 변수에 담음
        String requestURL = request.getRequestURL().toString(); //requestURL 이름만 쏙 뺌
        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.setRequestURL(requestURL); //requestURL MyLogger에 지정
        myLogger.log("controller test"); //메세지 지정
        logDemoService.logic(" testId"); //서비스 클래스에서
        return "OK";

    }

}

