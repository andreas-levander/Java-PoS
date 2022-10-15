package app.controller;

import app.errors.NotFoundException;
import app.model.BonusCard;
import app.model.tables.Bonus;
import app.service.BonusService;
import org.springframework.stereotype.Controller;

/** Class responsible controlling bonus points */
@Controller
public class BonusController {
    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    /** returns the bonus points of the customer, if customer is not found exception is thrown which causes the backend to respond with status 404 */
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
