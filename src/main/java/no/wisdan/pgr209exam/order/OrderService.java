package no.wisdan.pgr209exam.order;

import no.wisdan.pgr209exam.customer.Customer;
import no.wisdan.pgr209exam.exception.CustomerNotFoundException;
import no.wisdan.pgr209exam.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Order findById(long id) {
        Order order;
        try {
            order = orderRepo.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new OrderNotFoundException("Order " + id + "not found");
        }
        return order;
    }

    public void deleteById(Long id) {
        orderRepo.deleteById(id);
    }
    public void deleteAllOrders() {
        orderRepo.deleteAll();
    }

}
