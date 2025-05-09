package no.wisdan.pgr209exam.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.wisdan.pgr209exam.address.Address;
import no.wisdan.pgr209exam.order.Order;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "customer_gen")
    @SequenceGenerator(name = "customer_gen", sequenceName = "customer_seq", allocationSize = 1)
    @Column(name = "customer_id")
    private Long id;
    @Column(name = "customer_firstname")
    private String firstName;
    @Column(name = "customer_lastname")
    private String lastName;
    @Column(name = "customer_phonenumber")
    private String phoneNumber;
    @Column(name = "customer_email")
    private String email;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties({"customers", "orders"})
    private List<Order> orders;


    @JsonIgnoreProperties({"customers"})
    @ManyToMany
    @JoinTable(
            name = "address_customer",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Address> addresses;

    public Customer(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}

