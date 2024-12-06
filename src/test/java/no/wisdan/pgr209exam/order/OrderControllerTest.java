package no.wisdan.pgr209exam.order;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    static List<Order> orders = new ArrayList<>();
    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderService service;

    @BeforeAll

    static void setUpBeforeClass() {
        for (int i = 0; i < 10; i++) {
            orders.add(new Order(
                    new BigDecimal("1.00"),
                    new BigDecimal("100.00"),
                    true,
                    null,
                    null,
                    null
            ));
        }
    }

    @Test
    void getOrders() throws Exception {
        when(service.findAll()).thenReturn(orders);
        this.mockMvc.perform(get("/api/order"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getOrderById() throws Exception {
        when(service.findById(1)).thenReturn(orders.get(1));
        System.out.println(new ObjectMapper().writeValueAsString(service.findById(1)));
        this.mockMvc.perform(
                        get("/api/order/1"))
                .andExpect(status().isOk());
    }

@Test
void saveOrder() throws Exception {
    OrderDto orderDto = new OrderDto(
            1,
            List.of(1L, 2L),
            BigDecimal.valueOf(10.0),
            BigDecimal.valueOf(100.0),
            true,
            1
    );
    when(service.save(orderDto)).thenReturn(orders.get(1));

    this.mockMvc.perform(
            post("/api/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(orderDto))
    ).andExpect(status().isOk());
}

    @Test
    void deleteOrder() throws Exception {
        doNothing().when(service).deleteById(1L);
        this.mockMvc.perform(delete("/api/order/1"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    assert responseContent.contains("Order deleted");
                });
    }
}
