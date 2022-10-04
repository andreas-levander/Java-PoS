package app.service;

import app.errors.NoItemInProductCatalog;
import app.model.Price;
import app.model.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PriceService {
    private final PriceRepository repository;

    public PriceService(PriceRepository repository) {
        this.repository = repository;
    }

    public Price get(Integer id) {
        var test = repository.findById(id);
        if (test.isPresent()) return test.get();
        throw new NoItemInProductCatalog("price not found");
    }

    public void save(Price price) {
        repository.save(price);
    }

}
