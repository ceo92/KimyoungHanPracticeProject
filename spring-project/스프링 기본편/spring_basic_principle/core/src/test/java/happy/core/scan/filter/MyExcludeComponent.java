package happy.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)

@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
//이 친구가  붙은 놈은 컴포넌트 스캔에서 제외함
