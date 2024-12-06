package no.wisdan.pgr209exam.order;

import no.wisdan.pgr209exam.address.Address;
import no.wisdan.pgr209exam.address.AddressService;
import no.wisdan.pgr209exam.customer.Customer;
import no.wisdan.pgr209exam.customer.CustomerService;
import no.wisdan.pgr209exam.exception.OrderNotFoundException;
import no.wisdan.pgr209exam.product.Product;
import no.wisdan.pgr209exam.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final CustomerService customerService;
    private final ProductService productService;
    private final AddressService addressService;

    @Autowired
    public OrderService(OrderRepo orderRepo, CustomerService customerService, ProductService productService, AddressService addressService) {
        this.orderRepo = orderRepo;
        this.customerService = customerService;
        this.productService = productService;
        this.addressService = addressService;
    }


    public Order save(OrderDto orderDto) {
        Customer customer = customerService.findById(orderDto.customerId());

        List<Product> products = new ArrayList<>();
        for (long productId : orderDto.productId()) {
            products.add(productService.findById(productId));
        }

        Address shippingAddress = addressService.findById(orderDto.shippingAddressId());

        Order order = orderRepo.save(new Order(
                orderDto.shippingCharge(),
                orderDto.totalPrice(),
                orderDto.isShipped(),
                customer,
                products,
                shippingAddress
        ));

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
