package hello.servlet.web.frontcontroller.v5;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5" , urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String , Object> handlerMappingMap = new HashMap<>(); //그냥 컨트롤러 매핑 정보임 다만 모든 타입 받아야하니 Object일 뿐
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();//어댑터 목록임(핸들러와 연동 할)
    //어댑터 중 하나 반환
    public FrontControllerServletV5() {
        initHandlerMappingMap(); //있는 컨트롤러 전부 넣어놓음
        initHandlerAdapters(); //있는 어댑터 전부 넣어놓음
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form" , new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save" , new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members" , new MemberListControllerV3());
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form" , new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save" , new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members" , new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter()); // 일단 V3 어댑터만 등록했음 여러 어댑터 여기서 추가 가능
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request); //핸들러(컨트롤러)를 찾아오는 것이니

        if (handler==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //여기까진 똑같음

        //컨트롤러(핸들러)를 통하여 어댑터 찾아옴 어댑터를 통하여 실제 컨트롤러에 연결해야하니
        MyHandlerAdapter adapter = getHandlerAdapter(handler); //핸들러에 해당되는 어댑터 객체 반환(다형성 이용해서)




        ModelView modelView = adapter.handle(request, response, handler);

        String viewName = modelView.getViewName();
        MyView myView = viewResolver(viewName);

        myView.render(modelView.getModel() , request , response);
    }
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Object handler = handlerMappingMap.get(requestURI);
        return handler;
    }
    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);



    }


    private static MyView viewResolver(String viewName) {
        MyView myView = new MyView("/WEB-INF/view/"+ viewName +".jsp");
        return myView;
    }

}
