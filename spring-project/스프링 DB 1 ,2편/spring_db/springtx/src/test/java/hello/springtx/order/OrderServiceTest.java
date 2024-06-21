package hello.springtx.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class OrderServiceTest {


    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void order() throws NotEnoughMoneyException {
        Order order = new Order();
        order.setUsername("뿡뿡이");

        orderService.order(order);

        Order findOrder = orderRepository.findById(order.getId()).get();
        Assertions.assertThat(findOrder.getPayStatus()).isEqualTo("완료");
        //대기 , 완료 구분짓는 건 ENUM 쓰는 게 좋음

    }

    @Test
    void runtimeException() throws NotEnoughMoneyException {
        Order order = new Order();
        order.setUsername("잔고부족");

        Assertions.assertThatThrownBy(()->orderService.order(order)).isInstanceOf(NotEnoughMoneyException.class);

        Order findOrder = orderRepository.findById(order.getId()).orElseThrow();
        Assertions.assertThat(findOrder.getPayStatus()).isEqualTo("대기");
    }

    @Test
    void bizException(){
        Order order = new Order();
        order.setUsername("잔고부족");

        try{
            orderService.order(order);
        }catch (NotEnoughMoneyException e){
            log.info("고객에게 잔고 부족을 알리고 별도의 계좌로 입금하도록 안내");
        }

        Order findOrder = orderRepository.findById(order.getId()).get();
        Assertions.assertThat(findOrder.getPayStatus()).isEqualTo("대기");

    }
}