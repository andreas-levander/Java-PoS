package app.controller;

import app.model.ProductStat;
import app.model.Sale;
import app.model.tables.Bonus;
import app.model.tables.SoldProduct;
import app.service.BonusService;
import app.service.StatsService;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class StatsController {
    private final StatsService statsService;
    private final BonusService bonusService;

    public StatsController(StatsService statsService, BonusService bonusService) {
        this.statsService = statsService;
        this.bonusService = bonusService;
    }

    public void saveSaleStats(Sale sale) {
        saveBonus(sale);
        saveProducts(sale);
    }

    private void saveBonus(Sale sale) {
        var bonusCard = sale.getBonusCard();
        if (bonusCard == null) return;

        System.out.println(bonusCard.getNumber());
        var resp = bonusService.findCustomerByBonusCard(bonusCard.getNumber(), bonusCard.getGoodThruMonth(), bonusCard.getGoodThruYear());
        if (resp.isEmpty()) return;

        var customer = resp.get();
        System.out.println(customer.getCustomerNo());
        System.out.println(customer.getBonusCard().getNumber());
        System.out.println(customer.getFirstName());
        System.out.println(customer.getLastName());
        System.out.println(customer.getBonusCard().getBlocked());
        System.out.println(customer.getBonusCard().getExpired());
        if (customer.getBonusCard().getBlocked() || customer.getBonusCard().getExpired()) {
            return;
        }
        var bonusPoints = Math.round(sale.getCart().getTotalPrice().getNumber().doubleValue() * 0.1);
        bonusService.saveBonus(customer.getCustomerNo(), bonusPoints);

    }

    private void saveProducts(Sale sale) {
        var items = sale.getCart().getItems();
        var soldProducts = items.stream().map(item ->
                new SoldProduct(item.getId(), item.getBarCode(), item.getName(), sale.getDate())).toList();
        statsService.saveSoldProducts(soldProducts);
    }

    public List<ProductStat> getTopProducts(LocalDate start, LocalDate end) {
        return statsService.getTopProducts(start, end);
    }

    public List<ProductStat> getLeastSoldProducts(LocalDate start, LocalDate end) {
        return statsService.getLeastSoldProducts(start, end);
    }
}
