package app.controller;

import app.model.ProductStat;
import app.model.Sale;
import app.model.tables.Bonus;
import app.model.tables.SoldProduct;
import app.service.BonusService;
import app.service.StatsService;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.List;

/** Class responsible for controlling saving statistics and bonus points */
@Controller
public class StatsController {
    private final StatsService statsService;
    private final BonusService bonusService;
    private final double bonusPointsPercentOfSale = 0.1;

    public StatsController(StatsService statsService, BonusService bonusService) {
        this.statsService = statsService;
        this.bonusService = bonusService;
    }

    /** Saves stats form the sale */
    public void saveSaleStats(Sale sale) {
        var customerNr = saveBonus(sale);
        saveProducts(sale, customerNr);
    }
    /** @return id of the customer if found and saves bonus points if card is valid */
    private Integer saveBonus(Sale sale) {
        var bonusCard = sale.getBonusCard();
        if (bonusCard == null) return -1;

        System.out.println(bonusCard.getNumber());
        var resp = bonusService.findCustomerByBonusCard(bonusCard.getNumber(), bonusCard.getGoodThruMonth(), bonusCard.getGoodThruYear());
        if (resp.isEmpty()) return -1;

        var customer = resp.get();
        if (customer.getBonusCard().getBlocked() || customer.getBonusCard().getExpired()) {
            return -1;
        }
        var bonusPoints = Math.round(sale.getCart().getTotalPrice().getNumber().doubleValue() * bonusPointsPercentOfSale);
        bonusService.saveBonus(customer.getCustomerNo(), bonusPoints);

        return customer.getCustomerNo();
    }

    /** Saves stats of every product in the sale */
    private void saveProducts(Sale sale, int customerNr) {
        var items = sale.getCart().getItems();
        var soldProducts = items.stream().map(item ->
                new SoldProduct(item.getId(), customerNr, item.getBarCode(), item.getName(), sale.getDate())).toList();
        statsService.saveSoldProducts(soldProducts);
    }

    public List<ProductStat> getTopProducts(LocalDate start, LocalDate end) {
        return statsService.getTopProducts(start, end);
    }

    public List<ProductStat> getLeastSoldProducts(LocalDate start, LocalDate end) {
        return statsService.getLeastSoldProducts(start, end);
    }

    public List<ProductStat> getProductStat(String barCode) {
        return statsService.getProductStat(barCode);
    }

    public List<ProductStat> getCustomerStat(String id) {
        return statsService.getCustomerStat(Integer.parseInt(id));
    }

}
