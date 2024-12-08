package no.wisdan.pgr209exam.address;

import no.wisdan.pgr209exam.customer.Customer;
import no.wisdan.pgr209exam.customer.CustomerService;
import no.wisdan.pgr209exam.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AddressService {
    private final AddressRepo addressRepo;
    private final CustomerService customerService;

    @Autowired
    public AddressService(AddressRepo addressRepo, CustomerService customerService) {
        this.addressRepo = addressRepo;
        this.customerService = customerService;
    }

    public List<Address> findAll() {
        return addressRepo.findAll();
    }

    public Address findById(long id) {
        Address address;
        try {
            address = addressRepo.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundException("Address " + id + "not found");
        }
        return address;
    }

    public Address save(AddressDto addressDto) {
        List<Customer> customers = new ArrayList<>();
        for (long customerId : addressDto.customerId()) {
            customers.add(customerService.findById(customerId));
        }
        Address address = addressRepo.save(new Address(
                addressDto.street(),
                addressDto.city(),
                addressDto.zipcode(),
                customers

        ));

        return addressRepo.save(address);
    }

    public void deleteById(long id) {
        addressRepo.deleteById(id);
    }
}
