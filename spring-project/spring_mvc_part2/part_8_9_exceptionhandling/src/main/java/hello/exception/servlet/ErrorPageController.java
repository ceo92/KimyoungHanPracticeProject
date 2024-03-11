package hello.exception.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ErrorPageController {
    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type";
    public static final String ERROR_EXCEPTION = "jakarta.servlet.error.exception";
    public static final String ERROR_MESSAGE = "jakarta.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "jakarta.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request , HttpServletResponse response){
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }
    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request , HttpServletResponse response){
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    //클라이언트가 보내는 타입에 따라서 지정된 미디어 타입이 호출될 것이다라고 인지
    //즉 클라이언트가 Accept로 보내는 미디어 타입에 따라 호출되는 컨트롤러가 달라짐
    //Accept: application/json일 경우 해당 컨트롤러가 호출
    @RequestMapping(value = "/error-page/500" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String , Object>> errorPage500Api(HttpServletRequest request , HttpServletResponse response){

        log.info("API errorPage 500");
        Map<String, Object> result = new HashMap<>(); //json 응답 위해서
        Exception ex = (Exception)request.getAttribute(RequestDispatcher.ERROR_EXCEPTION); //예외 객체 생성
        result.put("status", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)); // 오류 상태코드 지정
        result.put("message", ex.getMessage()); //오류 메시지 지정

        //RequestDispatcher 안에 전부 정의되어있음
        Integer statusCode = (Integer)request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));




    }


    private void printErrorInfo(HttpServletRequest request){
        log.info("ERROR_EXCEPTION_TYPE: {}" , request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_EXCEPTION: {}" , request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_MESSAGE: {}" , request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}" , request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}" , request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}" , request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());
        log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        
    }
    




}
