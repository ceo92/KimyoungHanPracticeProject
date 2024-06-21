package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//프론트 컨트롤러는 서블릿이어야함
@WebServlet(name = "frontControllerServletV1",urlPatterns = "/front-controller/v1/*")
//*로 하면 front-controller 하위의 어떤 컨트롤러의 URI를 호출하더라도 일단  이 프론트 컨트롤러가 우선 호출됨 ㅇㅇ
public class FrontControllerServletV1 extends HttpServlet {
    //특정 URL이 호출되면 호출된 URL에 맞게 V1 인터페이스에 구현된 객체를 호출해 라는 메커니즘으로 구현할 것
    //URL 요청 시 프론트 컨트롤러가 요청에 맞는 컨트롤러 객체 매핑해서 반환해줌(즉 URL에 해당되는 객체 짝지어줌(매핑해줌))
    private Map<String , ControllerV1> controllerV1Map  = new HashMap<>();
    public FrontControllerServletV1() {
        //서블릿이 생성될 때 매핑 정보를 담아둘 것임
        controllerV1Map.put("/front-controller/v1/members/new-form" , new MemberFormControllerV1());
        controllerV1Map.put("/front-controller/v1/members/save" , new MemberSaveControllerV1());
        controllerV1Map.put("/front-controller/v1/members" , new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //URL이 아닌 URI임 즉 핵심 PATH만 가져오는 것 그 경로가 결국 사용자가 원하는 컨트롤러 경로일 테니
        String requestURI = request.getRequestURI();
        //사용자가 지정한 컨트롤러가 찾아짐 , 인터페이스를 구현받았으니 한 줄의 코드로 일관성 있게 사용(다형성 !)
        ControllerV1 controller = controllerV1Map.get(requestURI);
        if(controller ==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //예외처리 해줘야됨 사용자가 URL 잘못 입력햇을 경우 그런 경로  없다고404출력
            return;//바로 로직 종료됨
        }

        controller.process(request , response);

    }
}
