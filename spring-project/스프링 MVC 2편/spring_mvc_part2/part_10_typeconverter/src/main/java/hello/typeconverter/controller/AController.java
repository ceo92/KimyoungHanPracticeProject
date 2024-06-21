package hello.typeconverter.controller;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerExceptionResolver;

@RestControllerAdvice("hello.typeconverter.controller")
public class AController {


    @ExceptionHandler(RuntimeException.class)
    public ExTest aa(RuntimeException e){
        ExTest exTest = new ExTest(e.getClass().getName() , e.getMessage());
        return exTest;
    }

    @Data
    static class ExTest{
        private String exception;
        private String message;

        public ExTest(String exception, String message) {
            this.exception = exception;
            this.message = message;
        }
    }
}
