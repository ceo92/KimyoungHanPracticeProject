package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionServiceTest {

    //private DefaultConversionServiceImpl conversionService;

    @Autowired
    private ConversionService conversionService;

    @Test
    void conversionService(){
        //ConversionService 구현체
        DefaultConversionService conversionService = new DefaultConversionService();
        //등록
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());

        //사용
        //문자 => 숫자 변환기인 StringToInteger이 작동 알아서 해줌

        Assertions.assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
        Assertions.assertThat(conversionService.convert(10, String.class)).isEqualTo("10");
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));

        String ipPortString = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);
        assertThat(ipPortString).isEqualTo("127.0.0.1:8080");

    }

    @Component
    static class DefaultConversionServiceImpl extends DefaultConversionService{}




}
