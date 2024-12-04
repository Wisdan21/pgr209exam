package no.wisdan.pgr209exam.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.wisdan.pgr209exam.address.Address;
import no.wisdan.pgr209exam.customer.Customer;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orderdb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_gen")
    @SequenceGenerator(name = "order_gen", sequenceName = "order_seq", allocationSize = 1)
    @Column(name = "order_id")
    private Long id;
    @Column(name = "shipping_charge")
    private BigDecimal shippingCharge;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "is_shipped")
    private boolean isShipped;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("orders")
    private Customer customer;
    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )


    private List<Customer> products;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    public Order(BigDecimal shippingCharge, BigDecimal totalPrice, boolean isShipped, Customer customer, List<Customer> products, Address shippingAddress) {
        this.shippingCharge = shippingCharge;
        this.totalPrice = totalPrice;
        this.isShipped = isShipped;
        this.customer = customer;
        this.products = products;
        this.shippingAddress = shippingAddress;
    }

    public Order(BigDecimal shippingCharge, BigDecimal totalPrice, boolean isShipped) {
        this.shippingCharge = shippingCharge;
        this.totalPrice = totalPrice;
        this.isShipped = isShipped;
    }
}
