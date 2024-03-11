package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.StringTokenizer;

@Slf4j
public class StringToIpPortConverter implements Converter<String , IpPort> {
    @Override
    public IpPort convert(String source) {
        log.info("covert source = {}",source);
        StringTokenizer st = new StringTokenizer(source , ":");
        String ip = st.nextToken();
        int port = Integer.parseInt(st.nextToken());
        //앞에는 ip는 문자 , 뒤에 포트는 숫자로 쓰는 예제 만들어봤음
        IpPort ipPort = new IpPort(ip, port);
        return ipPort;

    }
}
