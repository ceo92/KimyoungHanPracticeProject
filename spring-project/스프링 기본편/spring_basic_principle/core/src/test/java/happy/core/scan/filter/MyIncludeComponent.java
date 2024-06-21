package happy.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)//@Target은 지금 이 어노테이션이 타입에 붙냐 필드에 붙냐 어디에 붙냐를 지정해줌 TYPE라고 하면 클래스 레벨에 붙는 것임!
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
//이 친구가  붙은 놈은 컴포넌트 스캔당한다고 생각
