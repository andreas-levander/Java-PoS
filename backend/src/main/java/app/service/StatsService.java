package app.service;

import app.repository.SoldProductsRepository;
import app.model.tables.SoldProduct;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatsService {
    private final SoldProductsRepository soldRepository;

    public StatsService(SoldProductsRepository soldRepository) {
        this.soldRepository = soldRepository;
    }

    public void saveSoldProducts(Iterable<SoldProduct> products) {
        soldRepository.saveAll(products);
    }

    public void getTopProducts(Date start, Date end) {
        System.out.println(start.toString() + " " + end.toString());
        var top = soldRepository.findTopProducts(start, end, PageRequest.of(0, 10));
        System.out.println(top.toString());
    }
}
