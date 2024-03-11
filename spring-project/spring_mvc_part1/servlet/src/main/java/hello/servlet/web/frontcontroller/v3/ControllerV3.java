package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public interface ControllerV3 {


    ModelView process(Map<String , String> paramMap);
    //ModelView는 나의 프레임워크 내부 코드임 즉 전혀 서블릿에 종속적이지 않음

}
