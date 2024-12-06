package no.wisdan.pgr209exam;

import com.github.javafaker.Faker;
import no.wisdan.pgr209exam.address.Address;
import no.wisdan.pgr209exam.address.AddressService;
import no.wisdan.pgr209exam.customer.Customer;
import no.wisdan.pgr209exam.customer.CustomerService;
import no.wisdan.pgr209exam.models.Status;
import no.wisdan.pgr209exam.order.Order;
import no.wisdan.pgr209exam.order.OrderDto;
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

        List<Customer> customers = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        for (int i = 1; i < 2; i++) {
            addresses.add(addressService.save(new Address(
                    faker.address().streetAddress(),
                    faker.address().city(),
                    faker.address().zipCode(),
                    getRandomCustomers(customers)
            )));
        }
        for (int i = 0; i < 3; i++) {
            customers.add(customerService.save(new Customer(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.phoneNumber().phoneNumber(),
                    faker.internet().emailAddress(),
                    getRandomAddresses(addresses)

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
        for (int i = 1; i < 2; i++) {
            addresses.add(addressService.save(new Address(
                    faker.address().streetAddress(),
                    faker.address().city(),
                    faker.address().zipCode(),
                    getRandomCustomers(customers)

            )));
        }
        for (int i = 1; i < 2; i++) {

            boolean isShipped = rng.nextBoolean();

            List<Long> productIds = new ArrayList<>();
            for (Product product : products) {
                productIds.add(product.getId());
            }

            Address shippingAddress = getRandomAddress(addresses);
            long shippingAddressId = shippingAddress.getId();


            Customer customer = getRandomCustomer(customers);
            long customerId = customer.getId();

            orders.add(orderService.save(new OrderDto(
                    customerId,
                    productIds,
                    new BigDecimal(faker.commerce().price(1, 3).replace(",", "")),
                    new BigDecimal(faker.commerce().price(10.0, 100.0).replace(",", "")),
                    isShipped,
                    shippingAddressId
            )));
        }
    }

    private Customer getRandomCustomer(List<Customer> customers) {
        return customers.get(rng.nextInt(customers.size()));
    }

    private Address getRandomAddress(List<Address> addresses) {
        return addresses.get(rng.nextInt(addresses.size()));
    }


    private List<Address> getRandomAddresses(List<Address> addresses) {
        List<Address> randomAddresses = new ArrayList<>();
        for (int i = 1; i < rng.nextInt(1, 2); i++) {
            randomAddresses.add(addresses.get(rng.nextInt(addresses.size())));

        }
        return randomAddresses;
    }

    private List<Customer> getRandomCustomers(List<Customer> customers) {
        List<Customer> randomCustomer = new ArrayList<>();
        for (int i = 1; i < rng.nextInt(1, 2); i++) {
            randomCustomer.add(customers.get(rng.nextInt(customers.size())));

        }
        return randomCustomer;
    }
}