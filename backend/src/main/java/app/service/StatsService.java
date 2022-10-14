package app.service;

import app.model.ProductStat;
import app.repository.SoldProductsRepository;
import app.model.tables.SoldProduct;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

import java.util.List;

@Service
public class StatsService {
    private final SoldProductsRepository soldRepository;

    public StatsService(SoldProductsRepository soldRepository) {
        this.soldRepository = soldRepository;
    }

    public void saveSoldProducts(Iterable<SoldProduct> products) {
        soldRepository.saveAll(products);
    }

    public List<ProductStat> getTopProducts(LocalDate start, LocalDate end) {
        // adding one day to include end day in result
        return soldRepository.findTopProducts(Date.valueOf(start), Date.valueOf(end.plusDays(1)), PageRequest.of(0, 10));
    }

    public List<ProductStat> getLeastSoldProducts(LocalDate start, LocalDate end) {
        // adding one day to include end day in result
        return soldRepository.findWorstProducts(Date.valueOf(start), Date.valueOf(end.plusDays(1)), PageRequest.of(0, 10));
    }

    public List<ProductStat> getProductStat(String barCode) {
        return soldRepository.findProductStats(barCode);
    }

    public List<ProductStat> getCustomerStat(Integer id) {
        return soldRepository.findCustomerStats(id);
    }



}
