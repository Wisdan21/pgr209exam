package no.wisdan.pgr209exam.address;

import no.wisdan.pgr209exam.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AddressService {
    private final AddressRepo addressRepo;

    @Autowired
    public AddressService(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
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

    public Address save(Address address) {
        return addressRepo.save(address);
    }

    public void deleteById(long id) {
        addressRepo.deleteById(id);
    }
    public void deleteAddress() {
        addressRepo.deleteAll();
    }
}
