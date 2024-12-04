package no.wisdan.pgr209exam.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.wisdan.pgr209exam.customer.Customer;

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

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Address(String street, String city, String zipcode, Customer customer) {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.customer = customer;
    }
}
