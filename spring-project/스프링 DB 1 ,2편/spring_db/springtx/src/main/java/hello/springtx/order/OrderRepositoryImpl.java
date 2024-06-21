package hello.springtx.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hello.springtx.order.QOrder.order;

@Repository
public class OrderRepositoryImpl{
    private EntityManager em;
    private JPAQueryFactory query;

    public OrderRepositoryImpl(EntityManager em) {
        this.em = em;
        query = new JPAQueryFactory(em);
    }

    //@Override
    public Order save(Order order) {
        em.persist(order);
        return order;
    }

//    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(em.find(Order.class , id));
    }

//    @Override
    public void update(Long orderId, Order order) {
        Order findOrder = em.find(Order.class, orderId);
        findOrder.setUsername(order.getUsername());
        findOrder.setPayStatus(order.getPayStatus());
    }

//    @Override
    public List<Order> findAll() {
        return query.select(order).from(order).where().fetch();
    }
}
