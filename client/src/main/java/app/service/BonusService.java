package app.service;

import app.model.Bonus;
import app.model.payment.BonusCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Optional;

/** Class responsible for doing network calls for bonus points */
@Service
public class BonusService {
    private final WebClient webClient;
    @Value("${backend.baseurl}")
    private String backendBaseUrl;

    public BonusService(WebClient webClient) {
        this.webClient = webClient;
    }

    // gets the customers bonus points from the backend
    public Optional<Bonus> getBonus(BonusCard bonusCard) {
        var response = webClient.get()
                .uri(backendBaseUrl +"/api/v1/bonus/card" + "?cardNr=" + bonusCard.getNumber() +
                        "&goodThruMonth=" + bonusCard.getGoodThruMonth() + "&goodThruYear=" + bonusCard.getGoodThruYear())
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(Bonus.class);
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        return Mono.empty();
                    } else {
                        throw new RuntimeException(clientResponse.toString());
                    }
                }).block();
        if (response == null) return Optional.empty();
        return Optional.of(response);
    }

    // sends the amount of bonus points a customer used to pay for a sale to the backend
    public void useBonus(Bonus bonus) {
        webClient.post()
                .uri(backendBaseUrl +"/api/v1/bonus/use")
                .bodyValue(bonus)
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
