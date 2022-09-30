package app.controller;

import app.controller.cart.CartController;
import app.model.Payment;
import app.model.Item;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@FxmlView("/SplitPaymentDialog.fxml")
@Component
public class SplitPaymentDialogController {
    private final CartController cartController;
    private Payment payment;
    private final FxWeaver fxWeaver;

    public SplitPaymentDialogController(CartController cartController, Payment payment, FxWeaver fxWeaver) {
        this.cartController = cartController;
        this.payment = payment;
        this.fxWeaver = fxWeaver;
    }

    private Stage stage;
    private SimpleDoubleProperty change;
    @FXML
    private AnchorPane dialog;
    @FXML
    private Button cancelButton;
    @FXML
    private Button payWithCardButton;
    @FXML
    private Button payWithCashButton;
    @FXML
    private ListView<Item> itemList;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setTitle("Split Payment");

        //totalLabel.textProperty().bind(saleController.getCurrentSale().getTotalPrice().asString());

        cancelButton.setOnAction((actionEvent -> {
            stage.close();
        }));

        payWithCardButton.setOnAction((actionEvent -> {
            Double cardTotal = calcSelectedTotal(itemList.getSelectionModel().getSelectedItems());
            Double total = cartController.getCurrentCart().getTotalPrice().get();
            try {
                payment.send(cardTotal);
                payment.result();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            payment.setCashTotal(total - cardTotal);
            fxWeaver.loadController(CashPaymentDialogController.class).show();
            stage.close();
        }));

        payWithCashButton.setOnAction((actionEvent -> {
            Double cashTotal = calcSelectedTotal(itemList.getSelectionModel().getSelectedItems());
            Double total = cartController.getCurrentCart().getTotalPrice().get();
            try {
                payment.send(total - cashTotal);
                payment.result();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            payment.setCashTotal(cashTotal);
            fxWeaver.loadController(CashPaymentDialogController.class).show();
            stage.close();
        }));

    }

    public Double calcSelectedTotal(ObservableList<Item> selected){
        Double selectedTotal = 0.0;
        for(int i = 0; i < selected.size(); i++){
            selectedTotal = selectedTotal + selected.get(i).getPrice();
        }
        return selectedTotal;
    }

    public void show() {
        stage.show();
        itemList.setItems(cartController.getCurrentCart().getCart());
        itemList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
