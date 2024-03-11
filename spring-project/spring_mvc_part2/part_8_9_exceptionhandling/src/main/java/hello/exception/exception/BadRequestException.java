package hello.exception.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST , reason = "error.bad") //원래는 500예외 터져야되지만,
public class BadRequestException extends RuntimeException{
    //어노테이션 하나만 붙이면 구현 ResponseStatusExceptionResolver이 다 해줌

}
