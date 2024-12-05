package no.wisdan.pgr209exam.customer;

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

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    static List<Customer> customers = new ArrayList<>();
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService service;

    @BeforeAll
    static void setUp() {
        for (int i = 0; i < 10; i++) {
            customers.add(new Customer("FirstName" + i, "LastName" + i, "983456789", "email" + i));
        }
    }

    @Test
    void getCustomer() throws Exception {
        when(service.findAll()).thenReturn(customers);
        this.mockMvc.perform(get("/api/customer"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerById() throws Exception {
        when(service.findById(1)).thenReturn(customers.get(1));
        System.out.println(new ObjectMapper().writeValueAsString(service.findById(1)));
        this.mockMvc.perform(
                        get("/api/customer/1"))
                .andExpect(status().isOk());
    }

    @Test
    void saveCustomer() throws Exception {
        when(service.save(new Customer())).thenReturn(customers.get(1));
        this.mockMvc.perform(
                post("/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customers.get(1)))
        ).andExpect(status().isOk());
    }

    @Test
    void deleteCustomer() throws Exception {
        doNothing().when(service).deleteById(1);
        this.mockMvc.perform(delete("/api/customer/1"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    assert responseContent.contains("Customer deleted");
                });
    }
}
