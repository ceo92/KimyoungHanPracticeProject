package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import lombok.Data;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class FormatterController {

    @GetMapping("/formatter/edit")
    public String formatterForm(Model model) {
        Form form = new Form();
        form.setNumber(10000);
        form.setLocalDateTime(LocalDateTime.now());
        model.addAttribute("form", form);
        return "formatter-form";
    }

    @PostMapping("/formatter/edit")
    public String formatterFormEdit(@ModelAttribute Form form){
        return "formatter-view";
    }
    
    @GetMapping("/asd")
    public String asd(Model model){
        model.addAttribute("ipPort" , new IpPort("127.1.1.0" , 8080));
        return "kyungmin";
        
    }

    @PostMapping("/asd")
    public String asd(@ModelAttribute IpPort ipPort){
        System.out.println("ipPort.getPort() = " + ipPort.getPort());
        System.out.println("ipPort.getIp() = " + ipPort.getIp());
        return "kyungmin-view";
        
    }


    @Data
    static class Form{
        @NumberFormat(pattern = "###,###")
        private Integer number; //1000 => String : "100,000"  역도 가능
        
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//월에 대한 건 대문자로 써야 분과 구분지을 수 있음
        private LocalDateTime localDateTime; //Date => String : "yyyy-~ " 역도 가능
    }

}
