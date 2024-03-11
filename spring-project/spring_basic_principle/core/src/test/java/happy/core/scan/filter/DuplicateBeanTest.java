package happy.core.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class DuplicateBeanTest {

    @Test
    void aa(){

    }

    @Configuration
    @ComponentScan
    static class Hello{

    }
}
