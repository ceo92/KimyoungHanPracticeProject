package hello.itemservice.domain.item;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
/**
 * FAST : 빠른  배송
 * NORMAL : 일반 배송
 * SLOW : 느린 배송
 */
public class DeliveryCode {
    private String code; //FAST
    private String displayName; //빠른 배송
    //즉 code는 시스템 내에서 쓰는 값 , displayName은 고객에게 보여주기위한 값
}
