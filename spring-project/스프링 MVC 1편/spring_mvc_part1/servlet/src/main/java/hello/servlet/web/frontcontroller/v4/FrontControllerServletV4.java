package hello.servlet.web.frontcontroller.v4;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4" , urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {
    private Map<String , ControllerV4> controllerV4Map = new HashMap<>();

    public FrontControllerServletV4(){
        controllerV4Map.put("/front-controller/v4/members/new-form" , new MemberFormControllerV4());
        controllerV4Map.put("/front-controller/v4/members/save" , new MemberSaveControllerV4());
        controllerV4Map.put("/front-controller/v4/members" , new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerV4Map.get(requestURI);
        if (controller==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //여기까진 똑같고

        Map<String, String> paramMap = createParamMap(request);
        Map<String , Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);


        MyView myView = viewResolver(viewName);


        myView.render(model , request , response); //모델도 넘겨줘야됨 JSP에서 동적인 HTML을 랜더링 하려면 ㅇㅇ


    }

    private static MyView viewResolver(String viewName) {
        MyView myView = new MyView("/WEB-INF/view/"+ viewName +".jsp");
        return myView;
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
