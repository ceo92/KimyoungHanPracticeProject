package hello.login.web.argumentresolver;

import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //해당 어노테이션은 파라메터에 넣을꺼야 !!
@Retention(RetentionPolicy.RUNTIME) //실제 동작할때까지 어노테이션 남아있어야해
public @interface Login {

}
