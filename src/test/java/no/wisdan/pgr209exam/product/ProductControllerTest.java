package no.wisdan.pgr209exam.product;

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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    static List<Product> products = new ArrayList<>();
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ProductService service;

    @BeforeAll

    static void setUpBeforeClass() {
        for (int i = 0; i < 10; i++) {
            products.add(new Product("Product" + i));
        }
    }
    @Test void getProducts() throws Exception {
        when(service.findAll()).thenReturn(products);
        this.mockMvc.perform(get("/api/product"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test void getProductById() throws Exception {
        when(service.findById(1)).thenReturn(products.get(1));
        System.out.println(new ObjectMapper().writeValueAsString(service.findById(1)));
        this.mockMvc.perform(
                        get("/api/product/1"))
                .andExpect(status().isOk());
    }

    @Test void saveProduct() throws Exception {
        when(service.save(new Product())).thenReturn(products.get(1));
        this.mockMvc.perform(
                post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(products.get(1)))
        ).andExpect(status().isOk());
    }

}
