package no.wisdan.pgr209exam.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.wisdan.pgr209exam.customer.Customer;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "address_gen")
    @SequenceGenerator(name = "address_gen", sequenceName = "address_seq", allocationSize = 1)
    @Column(name = "address_id")
    private Long id;
    private String street;
    private String city;
    private String zipcode;

    @ManyToMany
    @JoinTable(
            name = "address_customer",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")

    )
    @JsonIgnoreProperties({"orders", "addresses"})

    private List<Customer> customers;

    public Address(String street, String city, String zipcode, List<Customer> customers) {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.customers = customers;
    }


}
