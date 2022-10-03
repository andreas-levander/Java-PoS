package app.service;

import app.errors.ItemNotFoundException;
import app.model.payment.CardTransactionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/** Class responsible for calling the CardReader and CashBox */
@Service
public class PaymentService {
    private final WebClient webClient;
    private final XmlMapper xmlMapper;
    @Value("${cardreader.baseurl}")
    private String cardReaderBaseUrl;
    @Value("${cashbox.baseurl}")
    private String cashBoxBaseUrl;

    public PaymentService(WebClient webClient) {
        this.webClient = webClient;
        xmlMapper = new XmlMapper();
    }

    public String waitForPayment(String amount) {
        System.out.println(amount);
        return webClient.post()
                .uri(cardReaderBaseUrl +"/cardreader/waitForPayment")
                .body(BodyInserters.fromFormData("amount", amount))
                .accept(MediaType.TEXT_PLAIN)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
    }

    public String getStatus() {
        return webClient.get()
                .uri(cardReaderBaseUrl +"/cardreader/status")
                .accept(MediaType.TEXT_PLAIN)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
    }

    public CardTransactionResult getResult() {
        var response = webClient.get()
                .uri(cardReaderBaseUrl +"/cardreader/result")
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
        try {
            return xmlMapper.readValue(response, CardTransactionResult.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String abortCardPayment(){
        return webClient.post()
                .uri(cardReaderBaseUrl +"/cardreader/abort")
                .accept(MediaType.TEXT_PLAIN)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
    }

    public String openCashBox(){
        return webClient.post()
                .uri(cashBoxBaseUrl +"/cashbox/open")
                .accept(MediaType.TEXT_PLAIN)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
    }

    public String resetCardReader(){
        return webClient.post()
                .uri(cardReaderBaseUrl +"/cardreader/reset")
                .accept(MediaType.TEXT_PLAIN)
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
