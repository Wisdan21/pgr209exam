package no.wisdan.pgr209exam.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Address findById(Long id) {
        return addressRepo.findById(id).orElse(null);
    }

    public Address save(Address address) {
        return addressRepo.save(address);
    }

    public void deleteById(Long id) {
        addressRepo.deleteById(id);
    }
    public void deleteAddress() {
        addressRepo.deleteAll();
    }
}
