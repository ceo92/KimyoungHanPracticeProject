package hello.typeconverter.formatter;

import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.Formatter;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;

public class NumberFormatter implements Formatter<Number> {

    @Override
    public String print(Number number, Locale locale) {
        NumberFormat instance = NumberFormat.getInstance(locale);
        return instance.format(number);
    }



    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        NumberFormat instance = NumberFormat.getInstance(locale);
        return instance.parse(text);
    }
}
