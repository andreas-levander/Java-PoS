package app.controller;

import app.errors.NotFoundException;
import app.model.BonusCard;
import app.model.tables.Bonus;
import app.service.BonusService;
import org.springframework.stereotype.Controller;

@Controller
public class BonusController {
    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    public Bonus getBonus(BonusCard bonusCard) {
        var response = bonusService.findCustomerByBonusCard(bonusCard.getNumber(), bonusCard.getGoodThruMonth(), bonusCard.getGoodThruYear());
        if (response.isEmpty()) throw new NotFoundException("Bonus customer not found");

        var customer = response.get();
        var bonus = bonusService.getBonus(customer.getCustomerNo());
        if (bonus.isEmpty()) return new Bonus(customer.getCustomerNo(), 0L);

        return bonus.get();
    }

    public void useBonus(Bonus bonus) {
        bonusService.useBonus(bonus.getCustomerId(), bonus.getBonusPoints());
    }
}
