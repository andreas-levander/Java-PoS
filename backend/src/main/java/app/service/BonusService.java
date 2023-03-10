package app.service;

import app.model.Customer;
import app.model.tables.Bonus;
import app.repository.BonusRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

/** Class responsible for doing network/database calls for bonus points/customers */
@Service
public class BonusService {
    @Value("${bonusRegister.baseurl}")
    private String baseUrl;
    private final WebClient client;
    private final BonusRepository repository;
    private final XmlMapper xmlMapper;

    public BonusService(WebClient client, BonusRepository repository) {
        this.client = client;
        this.repository = repository;
        xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /** @return the customer with the given bonus card */
    public Optional<Customer> findCustomerByBonusCard(String number, String goodThruMonth, String goodThruYear) {
        var response = client.get()
                .uri(baseUrl +"/rest/findByBonusCard/" + number + "/" + goodThruYear + "/" + goodThruMonth)
                .accept(MediaType.APPLICATION_XML)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        return Mono.empty();
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
        if (response == null) return Optional.empty();
        try {
            var customer = xmlMapper.readValue(response, Customer.class);
            return Optional.of(customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /** database call to save bonus points */
    public void saveBonus(Integer id, Long bonusPoints) {
        repository.updateBonus(id, bonusPoints);
    }

    /** database call to get the bonus of given customer id */
    public Optional<Bonus> getBonus(Integer id) {
        return repository.findById(id);
    }

    /** database call to remove the given bonus points from customer with given id */
    public void useBonus(Integer id, Long amount) {
        repository.useBonus(id, amount);
    }
}
