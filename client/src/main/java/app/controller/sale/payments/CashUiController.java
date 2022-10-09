package app.controller.sale.payments;

import app.model.sale.Sale;
import app.model.sale.SaleFinishedEvent;
import app.model.sale.SaleStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/adminUI/payments/CashPaymentUi.fxml")
public class CashUiController {
    private final FxWeaver fxWeaver;
    private final ApplicationContext ctx;

    public CashUiController(FxWeaver fxWeaver, ApplicationContext ctx) {
        this.fxWeaver = fxWeaver;

        this.ctx = ctx;
    }

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label changeLabel;
    @FXML
    private Label cashTotalLabel;
    @FXML
    private AnchorPane changePane;

    private CashPaymentReceivedController cashPaymentDialog;
    private Sale sale;
    @FXML
    public void initialize() {
        cashPaymentDialog = fxWeaver.loadController(CashPaymentReceivedController.class);
        anchorPane.getChildren().setAll(cashPaymentDialog.getDialog());

        cashPaymentDialog.getConfirmButton().setOnAction(e ->{
            var amountReceived = cashPaymentDialog.getAmountReceivedField().textProperty().get();
            if (!NumberUtils.isParsable(amountReceived)) {
                cashPaymentDialog.showNotification("Amount not a number", Color.RED);
                return;
            }
            var x = Double.parseDouble(amountReceived);
            var change = x - sale.getCart().getTotalPrice().getNumber().doubleValue();
            if (change < 0) {
                cashPaymentDialog.showNotification("Amount received need to be >= total", Color.RED);
                return;
            }
            changeLabel.setText(String.valueOf(change));
            showChangePane();
            sale.setSaleStatus(SaleStatus.DONE);
            ctx.publishEvent(new SaleFinishedEvent(sale));

        });
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }


    public void bind(Sale sale) {
        this.sale = sale;
        cashTotalLabel.textProperty().bind(sale.getCart().getObservableTotalPrice().asString());
        cashPaymentDialog.bind(sale);
    }

    private void showChangePane() {
        anchorPane.getChildren().setAll(changePane);
    }

    public void showDialogPane() {
        anchorPane.getChildren().setAll(cashPaymentDialog.getDialog());
    }
}

