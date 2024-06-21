package hello.springmvc.basic.requestmapping;

import org.apache.tomcat.util.http.parser.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.invoker.HttpMethodArgumentResolver;

import java.awt.*;

@RestController
public class MappingController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/hello-basic" , method = RequestMethod.GET)
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    } //url로 매핑했으니 해당 컨트롤러(메서드)가 호출된 것

    @GetMapping("/mapping/{userid}/{orderid}")
    public String mappingPath(@PathVariable String userid , Long orderid){
        log.info("This is mapping Path");
        return "ok";
    }

    @GetMapping(value = "/mapping-get", headers = "mode=debug")
    public String mappingParam() {
        log.info("mappingParamGet");
        return "ok";
    }
    @PostMapping(value = "/mapping-post", params = "username=candy")
    public String mappingAsParam() {
        log.info("PostmappingParam");
        return "ok";
    }




    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsume2s() {
        log.info("mappingConsumes");

        return "ok";
    }

    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
