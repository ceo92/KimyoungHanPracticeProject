package hello.springtx.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//주문 고객에 대한 CRUD
public interface OrderRepository extends JpaRepository<Order , Long> {

    /*Order save(Order order);

    Optional<Order> findById(Long id);

    void update(Long orderId , Order order);

    List<Order> findAll();*/




}
