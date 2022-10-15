package app.api;

import app.controller.BonusController;
import app.model.BonusCard;
import app.model.tables.Bonus;
import org.springframework.web.bind.annotation.*;

/** Class responsible for the bonus endpoints */
@RestController
@RequestMapping(path = "api/v1/bonus")
public class BonusAPI {
    private final BonusController bonusController;

    public BonusAPI(BonusController bonusController) {
        this.bonusController = bonusController;
    }

    @GetMapping(path = "/card")
    public Bonus getBonus(@RequestParam String cardNr, @RequestParam String goodThruMonth, @RequestParam String goodThruYear) {
        System.out.println(cardNr + goodThruMonth + goodThruYear);
        var bonusCard = new BonusCard(cardNr, goodThruMonth, goodThruYear);
        return bonusController.getBonus(bonusCard);
    }

    @PostMapping(path = "/use")
    public void useBonus(@RequestBody Bonus bonus) {
        bonusController.useBonus(bonus);
    }
}
