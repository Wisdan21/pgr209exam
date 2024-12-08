package no.wisdan.pgr209exam.address;

import java.util.List;

public record AddressDto(String street,
                         String city,
                         String zipcode, List<Long> customerId) {
}

