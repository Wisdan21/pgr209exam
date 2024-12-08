package no.wisdan.pgr209exam;

import com.github.javafaker.Faker;
import no.wisdan.pgr209exam.address.Address;
import no.wisdan.pgr209exam.address.AddressDto;
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
    public void run(String... args) {

        List<Customer> customers = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            customers.add(customerService.save(new Customer(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.phoneNumber().phoneNumber(),
                    faker.internet().emailAddress()
            )));
        }

        for (Customer customer : customers) {
            int numAddresses = rng.nextInt(1, 4);
            List<Address> customerAddresses = new ArrayList<>();
            for (int i = 0; i < numAddresses; i++) {
                Address address = addressService.save(new AddressDto(
                        faker.address().streetAddress(),
                        faker.address().city(),
                        faker.address().zipCode(),
                        List.of(customer.getId())
                ));
                customerAddresses.add(address);
            }
            customer.setAddresses(customerAddresses);
            customerService.save(customer);
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

        for (int i = 0; i < 2; i++) {
            boolean isShipped = rng.nextBoolean();

            Customer randomCustomer = getRandomCustomer(customers);
            Address shippingAddress = getRandomAddress(randomCustomer.getAddresses());

            List<Long> productIds = new ArrayList<>();
            for (Product product : products) {
                productIds.add(product.getId());
            }

            orders.add(orderService.save(new OrderDto(
                    randomCustomer.getId(),
                    productIds,
                    new BigDecimal(faker.commerce().price(1, 3).replace(",", "")),
                    new BigDecimal(faker.commerce().price(10.0, 100.0).replace(",", "")),
                    isShipped,
                    shippingAddress.getId()
            )));
        }
    }

    private Customer getRandomCustomer(List<Customer> customers) {
        return customers.get(rng.nextInt(customers.size()));
    }

    private Address getRandomAddress(List<Address> addresses) {
        return addresses.get(rng.nextInt(addresses.size()));
    }

}