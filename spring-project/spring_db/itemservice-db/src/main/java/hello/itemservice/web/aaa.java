package hello.itemservice.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.config.SpringDataWebConfiguration;

@Slf4j
public class aaa {

    public static void main(String[] args) {
        String aaa= "hello";
        log.info("str = {}", aaa.concat(aaa).concat("aa").concat("bb"));
    }
}
