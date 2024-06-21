package hello.typeconverter.formatter;

import hello.typeconverter.type.IpPort;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.Formatter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;

public class IpPortFormatter implements Formatter<IpPort> {
    @Override
    public IpPort parse(String text, Locale locale) throws ParseException { //문자열 => IpPort
        StringTokenizer st = new StringTokenizer(text , ":");
        String ip = st.nextToken();
        int port = Integer.parseInt(st.nextToken());
        IpPort ipPort = new IpPort(ip, port);
        return ipPort;

    }

    @Override
    public String print(IpPort object, Locale locale) { //IpPort => 문자열
        String ip = object.getIp();
        int port = object.getPort();
        return ip + ":" + port;
    }
}
