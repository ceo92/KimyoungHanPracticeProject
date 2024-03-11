package hello.typeconverter.formatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MyNumberFormatterTest {
    @Test
    void parse() throws ParseException {
        MyNumberFormatter myNumberFormatter = new MyNumberFormatter();
        Number parse = myNumberFormatter.parse("1000", Locale.KOREA);
        System.out.println("parse = " + parse);
        //Number로 반환되지만 구현체가 있어야되기 때문에 기본으로 구현체 Long을 써서 반환됨
        assertThat(parse).isEqualTo(1000L);
    }

    @Test
    void print(){
        MyNumberFormatter myNumberFormatter = new MyNumberFormatter();
        Long value = 1000L;
        String print = myNumberFormatter.print(value, Locale.KOREA);
        System.out.println("print = " + print);
        assertThat(print).isEqualTo("1,000");
    }
}