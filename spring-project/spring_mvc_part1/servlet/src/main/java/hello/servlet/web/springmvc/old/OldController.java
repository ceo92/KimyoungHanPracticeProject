package hello.servlet.web.springmvc.old;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


@Component("/springmvc/old-controller") //스프링 빈 이름 지정 자동 컴포넌트 스캔 스프링 부트가 하면서 해당 객체 빈으로 등록할 것스프링 빈 이름이 oldController가 돼야되지만 url패턴 맞추기 위해 위에와 같이 이름 지정했음
public class OldController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form");
        //뷰를 못찾기 때문에 오류 뜬 것임
    }


}
