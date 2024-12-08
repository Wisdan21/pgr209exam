package no.wisdan.pgr209exam.address;

import no.wisdan.pgr209exam.customer.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
class AddressControllerTest {
    static List<Address> addresses = new ArrayList<>();
    static List<Customer> customers = new ArrayList<>();
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AddressService service;


    @BeforeAll
    static void setUpBeforeClass() {
        Customer customer = new Customer("John", "Doe", "983456789", "john.doe@example.com");
        customers.add(customer);

        for (int i = 0; i < 10; i++) {
            addresses.add(new Address("StreetT" + i, "City" + i, "Zip" + i, customers));
        }
    }

    @Test
    void getAddress() throws Exception {
        when(service.findAll()).thenReturn(addresses);
        this.mockMvc.perform(get("/api/address"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAddressById() throws Exception {
        when(service.findById(1)).thenReturn(addresses.get(1));
        System.out.println(new ObjectMapper().writeValueAsString(service.findById(1)));
        this.mockMvc.perform(
                        get("/api/address/1"))
                .andExpect(status().isOk());
    }

    @Test
    void saveAddress() throws Exception {
        when(service.save(new AddressDto(
                "streetT1",
                ",",
                "324",
                List.of(1L)

        ))).thenReturn(addresses.get(1));
        this.mockMvc.perform(
                post("/api/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addresses.get(1)))
        ).andExpect(status().isOk());
    }

    @Test
    void deleteAddress() throws Exception {
        doNothing().when(service).deleteById(1);
        this.mockMvc.perform(delete("/api/address/1"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    assert responseContent.contains("Deleted address with id 1");
                });
    }
}
