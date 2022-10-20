package app;

import app.model.*;
import app.model.tables.Price;
import app.service.BonusService;
import app.service.PriceService;
import app.service.StatsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureMockMvc
class StatisticsIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private StatsService statsService;
    @MockBean
    private BonusService bonusService;
    @Autowired
    private ObjectMapper mapper;

    private Sale sale;
    private final Integer customerNr = 54;


    @BeforeEach
    void beforeEach() {
        var item = new Item("test",1,"123", List.of(), Money.of(100, "EUR"));

        sale = new Sale(
                new Cart(List.of(item), UUID.randomUUID(),Money.of(100, "EUR")),
                UUID.randomUUID(),
                new BonusCard("123","1","2025"),
                new Date());
        Mockito.when(bonusService.findCustomerByBonusCard("123", "1", "2025"))
                .thenReturn(Optional.of(new Customer(customerNr, "test", "customer", "11",
                        new BonusCard("123", "1", "2025",false, false),"asd")));
    }

    @Test
    void sale_stats_is_saved() throws Exception {
        // when
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/stats/save/sale")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sale)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // then
        Mockito.verify(statsService, Mockito.times(1)).saveSoldProducts(Mockito.any());
        Mockito.verify(bonusService).findCustomerByBonusCard("123", "1", "2025");
        Mockito.verify(bonusService).saveBonus(customerNr, 10L);
    }
}
