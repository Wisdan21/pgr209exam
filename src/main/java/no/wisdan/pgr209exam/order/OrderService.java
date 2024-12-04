package no.wisdan.pgr209exam.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Order save(Order order) {
        return orderRepo.save(order);
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public Order findById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        orderRepo.deleteById(id);
    }
    public void deleteAllOrders() {
        orderRepo.deleteAll();
    }

}
