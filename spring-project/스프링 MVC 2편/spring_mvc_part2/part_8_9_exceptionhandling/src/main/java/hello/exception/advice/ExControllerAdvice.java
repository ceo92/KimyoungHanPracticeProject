package hello.exception.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;


@RestControllerAdvice
@Slf4j
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }    //이 컨트롤러 내부에서 발생한 IllegalArgumentException은 얘가 잡고 내부 로직이 호출 , @ResponseBody이니 Json 스타일로 응답

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult result = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    //이 컨트롤러 내부에서 발생한 IllegalArgumentException은 얘가 잡고 내부 로직이 호출 , @ResponseBody이니 Json 스타일로 응답

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler//Exception처리이니 위에서 처리하지 못한 예외들 여기서 전부 처리 , 즉 @ExceptionHandler 형태는 자식까지 다 처리함
    public ErrorResult exHandler(Exception e){
        log.error("[exceptionHandler ex ]", e);
        return new ErrorResult("EX", "내부 오류");
    }
    //여기다 두면 깔끔하게 분리됨 정상 / 예외 로직
}

