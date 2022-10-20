package app;

import app.model.Item;
import app.model.tables.Price;
import app.service.PriceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javamoney.moneta.Money;
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

import java.util.List;

@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureMockMvc
class PriceIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private PriceService priceService;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void test() throws Exception {
        var item = new Item("test",1,"123", List.of(), Money.of(100, "EUR"));

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/price/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(item)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        Mockito.verify(priceService, Mockito.times(1)).save(Mockito.any(Price.class));
    }
}
