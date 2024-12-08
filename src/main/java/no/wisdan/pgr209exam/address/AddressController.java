package no.wisdan.pgr209exam.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @GetMapping
    public ResponseEntity<List<Address>> getAddresses() {
        return ResponseEntity.ok(addressService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.save(addressDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable long id) {
        addressService.deleteById(id);
        return ResponseEntity.ok("Deleted address with id " + id);
    }

}
