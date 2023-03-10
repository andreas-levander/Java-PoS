package app.service;

import app.model.tables.Price;
import app.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Service responsible for saving/getting prices from database  */
@Service
public class PriceService {
    private final PriceRepository repository;

    public PriceService(PriceRepository repository) {
        this.repository = repository;
    }

    public Optional<Price> get(Integer id) {
        return repository.findById(id);
    }

    public void save(Price price) {
        repository.save(price);
    }

}
