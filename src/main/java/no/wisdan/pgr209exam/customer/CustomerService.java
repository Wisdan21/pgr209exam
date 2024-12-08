package no.wisdan.pgr209exam.customer;

import no.wisdan.pgr209exam.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Customer findById(long id) {
        Customer customer;
        try {
            customer = customerRepo.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundException("Customer " + id + "not found");
        }
        return customer;
    }

    public void deleteById(long id) {
        customerRepo.deleteById(id);
    }
}
