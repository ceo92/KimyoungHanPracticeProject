package hello.typeconverter.converter;


import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterTest {

    @Test
    public void stringToIntegerTest(){
        StringToIntegerConverter stringToIntegerConverter = new StringToIntegerConverter();
        String value = "178";
        Integer result = stringToIntegerConverter.convert(value);
        assertThat(result).isEqualTo(178);
    }

    @Test
    public void integerToStringTest(){
        IntegerToStringConverter converter = new IntegerToStringConverter();
        String result = converter.convert(2500);
        assertThat(result).isEqualTo("2500");
    }
    
    @Test
    public void stringToIpPortTest(){
        StringToIpPortConverter converter = new StringToIpPortConverter();
        IpPort convert = converter.convert("100.100.100:180");
        int port = convert.getPort();
        String ip = convert.getIp();
        assertThat(convert).isEqualTo(new IpPort("100.100.100", 180));

    }
    
    
    @Test
    public void ipPortToStringTest(){
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort ipPort = new IpPort("128.110.64", 128);
        String result = converter.convert(ipPort);
        assertThat(result).isEqualTo("128.110.64:128");
    }
    
    
    
    
    
    
}
