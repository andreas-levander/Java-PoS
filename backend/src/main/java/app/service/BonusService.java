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

    public Optional<Customer> findCustomerByBonusCard(String number, String goodThruMonth, String goodThruYear) {
        System.out.println(baseUrl +"/rest/findByBonusCard/" + number + "/" + goodThruYear + "/" + goodThruMonth);
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
            System.out.println(response);
            var customer = xmlMapper.readValue(response, Customer.class);
            return Optional.of(customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBonus(Integer id, Long bonusPoints) {
        repository.updateBonus(id, bonusPoints);
    }

    public Optional<Bonus> getBonus(Integer id) {
        return repository.findById(id);
    }

    public void useBonus(Integer id, Long amount) {
        System.out.println(id + " " + amount);
        repository.useBonus(id, amount);
    }
}
