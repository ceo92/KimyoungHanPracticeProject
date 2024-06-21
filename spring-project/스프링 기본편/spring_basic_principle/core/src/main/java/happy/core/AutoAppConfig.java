package happy.core;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration //그냥 싱글톤용으로 두는 것!! 설정 정보 클래스로 지정되긴 하지만 싱글톤 이거 없으면 보장 못하니 ㅇㅇ
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION , classes = Configuration.class)

)
public class AutoAppConfig {



}


