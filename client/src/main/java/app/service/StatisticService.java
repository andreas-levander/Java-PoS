package app.service;

import app.model.ProductStatistic;
import app.model.sale.Sale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/** Class responsible for doing network calls for statistics */
@Service
public class StatisticService {
    private final WebClient webClient;
    @Value("${backend.baseurl}")
    private String backendBaseUrl;

    public StatisticService(WebClient webClient) {
        this.webClient = webClient;
    }

    // to be used for saving sale related stats to the backend
    public void saveSale(Sale sale) {
        webClient.post()
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

    // gets the 10 most sold products from backend in the date range, should always get a response (empty array on no products)
    public List<ProductStatistic> getMostSoldProducts(LocalDate start, LocalDate end) {
        var response = webClient.get()
                .uri(backendBaseUrl +"/api/v1/stats/mostSold" + "?start=" + start +"&end=" + end)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(ProductStatistic[].class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();

        assert response != null;
        return Arrays.asList(response);
    }

    // gets the 10 least sold products from backend in the date range, should always get a response (empty array on no products)
    public List<ProductStatistic> getLeastSoldProducts(LocalDate start, LocalDate end) {
        var response = webClient.get()
                .uri(backendBaseUrl +"/api/v1/stats/leastSold" + "?start=" + start +"&end=" + end)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(ProductStatistic[].class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();

        assert response != null;
        return Arrays.asList(response);
    }

    // gets stats for a single product (amount sold per date), should always get a response (empty array on no sales)
    public List<ProductStatistic> getProductStats(String barCode) {
        var response = webClient.get()
                .uri(backendBaseUrl +"/api/v1/stats/barCode/" + barCode)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(ProductStatistic[].class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();

        assert response != null;
        return Arrays.asList(response);
    }

    // gets all purchases made by the customer, should always get a response (empty array on no purchases)
    public List<ProductStatistic> getCustomerStats(String id) {
        var response = webClient.get()
                .uri(backendBaseUrl +"/api/v1/stats/customer/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(ProductStatistic[].class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();

        assert response != null;
        return Arrays.asList(response);
    }
}
