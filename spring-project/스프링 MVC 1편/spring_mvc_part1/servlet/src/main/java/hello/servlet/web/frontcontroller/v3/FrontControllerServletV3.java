package hello.servlet.web.frontcontroller.v3;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3" , urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String , ControllerV3> controllerV3Map = new HashMap<>();
    public FrontControllerServletV3(){
        controllerV3Map.put("/front-controller/v3/members/new-form" , new MemberFormControllerV3());
        controllerV3Map.put("/front-controller/v3/members/save" , new MemberSaveControllerV3());
        controllerV3Map.put("/front-controller/v3/members" , new MemberListControllerV3());
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV3 controller = controllerV3Map.get(requestURI);
        if (controller==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //여기까진 똑같고

        Map<String, String> paramMap = createParamMap(request);//을 꺼냄 , 컨트롤러에 넘겨줄 파라메터Map의 키가 이름이 되고 값은 그 값이 됨
        //디테일한 로직이니 메서드로 뽑는 게 좋음

        ModelView mv = controller.process(paramMap); //뷰 논리 이름 및 모델 반환
        System.out.println("mv.getModel() = " + mv.getModel());
        String viewName = mv.getViewName(); //뷰의 논리 이름 반환
        MyView myView = viewResolver(viewName); //완성된 물리 이름의 MyView 반환
        // MyView 객체 자체가 뷰의 전체 이름을 저장하는 객체임 , 즉 viewResolver을 통하여 논리 이름을 줬더니 전체 뷰 이름을 가진 MyView객체를 반환해주는 것
        //그냥 물리 주소만 반환하면 MyView 목적이 사라지니 그냥 viewResolver 거치자마자 MyView 객체가 생성되며 값이 초기화되는 것


        myView.render(mv.getModel() , request , response); //모델도 넘겨줘야됨 JSP에서 동적인 HTML을 랜더링 하려면 ㅇㅇ


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
