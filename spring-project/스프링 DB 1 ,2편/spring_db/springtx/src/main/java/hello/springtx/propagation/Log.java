package hello.springtx.propagation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter @Setter
public class Log {
    @Id @GeneratedValue //값을 DB에서 할당해줌 !
    private Long id;
    private String message;

    public Log(){

    }

    public Log(String message){
        this.message = message;
    }







}
