package no.wisdan.pgr209exam.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }
    public void deleteAllCustomers() {
        customerRepo.deleteAll();
    }
}
