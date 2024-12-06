package no.wisdan.pgr209exam;

import com.github.javafaker.Faker;
import no.wisdan.pgr209exam.address.AddressService;
import no.wisdan.pgr209exam.customer.Customer;
import no.wisdan.pgr209exam.customer.CustomerService;
import no.wisdan.pgr209exam.models.Status;
import no.wisdan.pgr209exam.order.OrderService;
import no.wisdan.pgr209exam.product.Product;
import no.wisdan.pgr209exam.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            customers.add(customerService.save(new Customer(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.phoneNumber().phoneNumber(),
                    faker.internet().emailAddress()
            )));
        }
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            products.add(productService.save(new Product(
                    faker.commerce().productName(),
                    faker.lorem().sentence(),
                    new BigDecimal(faker.commerce().price(10, 100).replace(",", "")),
                    Status.AVAILABLE,
                    rng.nextInt(1, 20)
            )));
        }

    }

    private List<Customer> getRandomCustomers(List<Customer> customers) {
        List<Customer> randomCustomers = new ArrayList<>();
        for (int i = 0; i < rng.nextInt(1, 3); i++) {
            randomCustomers.add(customers.get(rng.nextInt(customers.size())));
        }
        return randomCustomers;
    }

    private List<Product> getRandomProducts(List<Product> products) {
        List<Product> randomProducts = new ArrayList<>();
        int numProducts = rng.nextInt(1, 4);
        for (int i = 0; i < numProducts; i++) {
            randomProducts.add(products.get(rng.nextInt(products.size())));
        }
        return randomProducts;
    }
}