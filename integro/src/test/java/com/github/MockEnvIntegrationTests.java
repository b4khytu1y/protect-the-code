package com.github;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import javax.money.Monetary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class MockEnvIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateClient exchangeRateClient;

    @Test
    void createOrder() throws Exception {
        // TODO: протестируйте успешное создание заказа на 100 евро
        String jsonOrder = """
                {
                    "amount": "EUR100.0"
                }
                """;
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOrder))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Sql("/unpaid-order.sql")
    void payOrder() throws Exception {
        String paymentReq = """
                {
                    "creditCardNumber": "3305649800973921"
                }
                """;
        // TODO: протестируйте успешную оплату ранее созданного заказа валидной картой
        mockMvc.perform(post("/order/{id}/payment", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentReq))
                .andExpect(status().isCreated());
    }

    @Test
    @Sql("/paid-order.sql")
    void getReceipt() throws Exception {
        // TODO: Протестируйте получение чека на заказ №1 c currency = USD
        // Примечание: используйте мок для ExchangeRateClient
        when(exchangeRateClient.getExchangeRate(
                Monetary.getCurrency("EUR"),
                Monetary.getCurrency("USD")))
                .thenReturn(BigDecimal.valueOf(0.8412));

        mockMvc.perform(get("/order/{id}/receipt?currency=USD", 1))
                .andExpect(status().isOk());
    }
}