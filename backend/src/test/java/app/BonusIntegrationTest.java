package app;

import app.model.BonusCard;
import app.model.Customer;
import app.service.BonusService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureMockMvc
public class BonusIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BonusService bonusService;

    @Test
    void get_bonus_successful_when_customer_found() throws Exception {
        Integer customerNr = 13;
        Mockito.when(bonusService.findCustomerByBonusCard("123", "1", "2025"))
                .thenReturn(Optional.of(new Customer(customerNr, "test", "customer", "11",
                        new BonusCard("123", "1", "2025",false, false),"asd")));

        // when
        mvc.perform(get("/api/v1/bonus/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("cardNr","123")
                        .queryParam("goodThruMonth", "1")
                        .queryParam("goodThruYear", "2025")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.customerId", Matchers.is(customerNr)))
                .andExpect(jsonPath("$.bonusPoints", Matchers.is(0)));
    }

    @Test
    void get_bonus_not_found_when_customer_not_found() throws Exception {
        Mockito.when(bonusService.findCustomerByBonusCard("123", "1", "2025"))
                .thenReturn(Optional.empty());

        // when
        mvc.perform(get("/api/v1/bonus/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("cardNr","123")
                        .queryParam("goodThruMonth", "1")
                        .queryParam("goodThruYear", "2025")
                )
                .andExpect(status().isNotFound());
    }
}
