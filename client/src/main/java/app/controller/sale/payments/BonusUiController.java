package app.controller.sale.payments;

import app.model.Bonus;
import app.model.sale.Sale;
import app.model.sale.SaleFinishedEvent;
import app.service.BonusService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@FxmlView("/adminUI/payments/BonusUi.fxml")
public class BonusUiController {
    private final ApplicationContext ctx;
    @FXML
    private AnchorPane bonusUiAnchorPane;
    @FXML
    private Label bonusIdLabel, bonusPointsLabel, message;
    @FXML
    private Button confirmBtn;

    public BonusUiController(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public AnchorPane getBonusUiAnchorPane() {
        return bonusUiAnchorPane;
    }
    private Sale sale;
    private Bonus bonus;

    @FXML
    public void initialize() {
        confirmBtn.setOnAction(actionEvent -> {
            bonus.setBonusPoints((long)Math.ceil(sale.getCart().getTotalPrice().getNumber().doubleValue()));
            ctx.getBean(BonusService.class).useBonus(bonus);
            ctx.publishEvent(new SaleFinishedEvent(sale));
            message.setText("Paid");
            confirmBtn.setDisable(true);
        });
    }

    public void showBonus(Optional<Bonus> bonusResp, Sale sale) {
        if (bonusResp.isEmpty()) {
            bonusIdLabel.setText("Bonus customer not found");
            return;
        }
        this.sale = sale;
        bonus = bonusResp.get();

        bonusIdLabel.setText("Customer id: " + bonus.getCustomerId());
        bonusPointsLabel.setText("Current bonus points: " + bonus.getBonusPoints());

        Long totalPriceRounded = (long) Math.ceil(sale.getCart().getTotalPrice().getNumber().doubleValueExact());

        if (bonus.getBonusPoints() < totalPriceRounded) {
            message.setText("Not enough bonus points to pay for sale");
            message.setTextFill(Color.RED);
            confirmBtn.setDisable(true);
            return;
        }
        message.setText("Use " + totalPriceRounded + " bonus points to pay for sale?");
        message.setTextFill(Color.GREEN);
        confirmBtn.setDisable(false);

    }
}
