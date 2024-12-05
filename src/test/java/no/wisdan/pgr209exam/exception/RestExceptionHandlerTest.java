package no.wisdan.pgr209exam.exception;

import no.wisdan.pgr209exam.customer.CustomerController;
import no.wisdan.pgr209exam.product.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class RestExceptionHandlerTest {

    @Autowired
    private CustomerController customerController;

    @Autowired
    private ProductController productController;


    @Autowired
    private RestExceptionHandler restExceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController, productController)
                .setControllerAdvice(restExceptionHandler)
                .build();
    }

    @Test
    void testHandleCustomerNotFoundException() throws Exception {
        mockMvc.perform(get("/api/customer/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.reason").value("Customer not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

}
