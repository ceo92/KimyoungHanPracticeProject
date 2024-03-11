package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {


    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
        //컨트롤러V3를 인터페이스 구현 객체를 클라가 요청하면 TRUE가 반환 , 응 나는 ControllerV3이 맞아
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV3 controller = (ControllerV3) handler;// supports로 ControllerV3인지를 검증했기 때문에 캐스팅 해서 쓸 수 있음
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);
        return mv;
    }
    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String , String> paramMap = new HashMap<>();

        //paramMap을 넘겨줘야됨 즉 폼에서 입력받은 파라메터 정보를 전부 다 컨트롤러에 반환해 처리해줘야됨
        // 여기야 두 개 username , age였지 원래는 ㅈㄴ 많을 테니 정형화를 위해서 iter로 전부 다 꺼내주는  로직으로 가자!
        request.getParameterNames().asIterator().forEachRemaining(
                paramName->paramMap.put(paramName, request.getParameter(paramName))
        );
        return paramMap;
    }


}
