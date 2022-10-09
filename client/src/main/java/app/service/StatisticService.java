package app.service;

import app.model.sale.Sale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Service
public class StatisticService {
    private final WebClient webClient;
    @Value("${backend.baseurl}")
    private String backendBaseUrl;

    public StatisticService(WebClient webClient) {
        this.webClient = webClient;
    }
    // to be used for saving a price to the backend
    public String saveSale(Sale sale) {
        return webClient.post()
                .uri(backendBaseUrl +"/api/v1/stats/save/sale")
                .bodyValue(sale)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
    }
}
