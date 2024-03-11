package happy.core.order;

import happy.core.member.Grade;
import happy.core.member.Member;

public interface OrderService {

    Order createOrder(Long memberId , String itemName , int itemPrice);
        //2. 회원 조회와 3. 할인 적용이라는 로직이 있다는 뜻

}
