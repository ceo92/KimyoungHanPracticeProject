package happy.core.lifecycle;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

//가짜 네트워크 클라이언트임!
public class NetworkClient{
    //접속할 서버의 url
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출 , url = "+url);

    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작 시 호출
    public void connect(){
        System.out.println("connect "+url);//연결될때 메세지 출력용
    }

    //연결이 된 상태에서 call()을 부를 수 있음
    public void call(String message){
        System.out.println("call "+ url + " message = " + message);
    }

    public void disconnect(){
        System.out.println("close : "+url); //해지될때 메세지 출력용
    }
    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        disconnect();
    }


}
