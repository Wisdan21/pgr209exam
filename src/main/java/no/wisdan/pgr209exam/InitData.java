package no.wisdan.pgr209exam;

import com.github.javafaker.Faker;
import no.wisdan.pgr209exam.address.AddressService;
import no.wisdan.pgr209exam.customer.CustomerService;
import no.wisdan.pgr209exam.order.OrderService;
import no.wisdan.pgr209exam.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class InitData implements CommandLineRunner {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final ProductService productService;
    private final AddressService addressService;
    private final Faker faker = Faker.instance();
    private final Random rng = new Random();

    @Autowired
    public InitData(CustomerService customerService, OrderService orderService, ProductService productService, AddressService addressService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.productService = productService;
        this.addressService = addressService;
    }


    @Override
    public void run(String... args) throws Exception {
//Legge til order og address her
        /*
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            customers.add(customerService.save(new Customer(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.phoneNumber().phoneNumber(),
                    faker.internet().emailAddress()

            )));
        }
        /*
        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            addresses.add(addressService.save(new Address(
                    faker.address().streetAddress(),
                    faker.address().city(),
                    faker.address().zipCode()

            )))
        }

        private

    }

         */

    }
}
