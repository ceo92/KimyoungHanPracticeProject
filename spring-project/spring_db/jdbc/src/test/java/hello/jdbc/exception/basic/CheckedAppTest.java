package hello.jdbc.exception.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

public class CheckedAppTest {


    @Test
    void unChecked(){
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request()).isInstanceOf(Exception.class);
    }

    static class Controller{
        Service service = new Service();
        public void request() {
            service.logic();
        }
    }

    static class Service{
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        //이렇게 체크예외 쓰면 throws로 예외 하나하나 지정해서 다 던짐 , 그래서 throws Exception 하면 서비스에서 잡아야되는 예외까지 던지는 불상사가 생김
        public void logic(){
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient{
        public void call(){
            //이건 SQL과 다르게 그냥 메시지 던짐
            throw new RuntimeConnectException("연결 실패");
        }
    }

    static class Repository{
        public void call(){
            try {
                runSQL();
            }catch (SQLException e){
                //이전 예외까지 런타임예외가 포함해서 생성
                // 체크 예외 + 언체크 예외 , 둘 다 출력
                throw new RuntimeSQLException(e);
            }
        }


        public void runSQL() throws SQLException {
            throw new SQLException("ex"); //JDBC 기술 쓰면 해당 예외 무조건 던져줘야함
        }
    }

    static class RuntimeConnectException extends RuntimeException{
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException{
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        } //이전 예외까지 같이 넣을 수 있음
    }
}
