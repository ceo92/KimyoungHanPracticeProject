package hello.servlet.web.frontcontroller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class MyView {
    private String viewPath;

    public MyView(String viewPath){
        this.viewPath = viewPath;
    }
    //기존에 JSP로 이동하는 것을 랜더링 한다로 표현할 것임
    // 실제 뷰가 랜더링되도록 동작하는 것이니
    public void render(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request , response); //포워드 = 랜더링
    }

    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request , response);
    }

    private static void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach((key , value) -> request.setAttribute(key , value)); //각 컨트롤러로부터 반환받은 모델들 전부 꺼내서 setAttribute에 저장
        //jsp와 서블릿만 사용할 거면 일단 setAttribute를 해주긴 해야도
    }

    //즉
}
