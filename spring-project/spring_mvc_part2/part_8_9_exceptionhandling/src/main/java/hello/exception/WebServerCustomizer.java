package hello.exception;

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

//스프링 부트 사
//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    //이름 그대로 웹 서버(톰캣)를 커스터마이징 하는 것임
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        //404 발생 시 해당 페이지로 이동되게끔(해당 컨트롤러로 이동되게끔)함
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        //ㄹㅇ 실제 예외 터질 경우 , RuntimeException의 자식들 까지도 전부 500 예외 페이지로 보 냄

        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");
        factory.addErrorPages(errorPage404 , errorPage500 , errorPageEx);

    }
}
