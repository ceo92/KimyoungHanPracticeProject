package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {
    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text= {} , locale={}",text , locale);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);//문자열을 객체로 파싱
    }

    @Override
    public String print(Number object, Locale locale) {
        log.info("object = {} , locale = {}", object, locale);
        NumberFormat instance = NumberFormat.getInstance(locale);
        return instance.format(object); //객체를 문자열로 포맷팅
    }
}
