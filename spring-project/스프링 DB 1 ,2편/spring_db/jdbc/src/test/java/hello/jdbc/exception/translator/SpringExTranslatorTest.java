package hello.jdbc.exception.translator;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class SpringExTranslatorTest {
    DataSource dataSource;
    @BeforeEach
    void init(){
        dataSource = new DriverManagerDataSource(URL , USERNAME , PASSWORD);
    }

    @Test
    void exceptionTranslator(){
        String sql = "select aaa";

        try{
            Connection con = dataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeQuery();
        }catch (SQLException e){
            Assertions.assertThat(e.getErrorCode()).isEqualTo(42122);

            //에러 코드 => 스프링 데이터 예외로 변환
            SQLErrorCodeSQLExceptionTranslator exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            //스프링 예외를 이 한줄로 변환 , 여기에 스프링에서 제공하는 데이터 접근 시 발생한 예외가 담김
            DataAccessException resultEx = exTranslator.translate("select", sql, e);
            log.info("resultEx" , resultEx);
            Assertions.assertThat(resultEx.getClass()).isEqualTo(BadSqlGrammarException.class);
        }
    }
}
