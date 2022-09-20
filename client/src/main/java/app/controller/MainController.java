package app.controller;

import app.model.Item;
import app.model.Sale;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.scene.control.Button;


@Component
@FxmlView("/MainController.fxml")
public class MainController {
    private final SaleController saleController;

    private final FxWeaver fxWeaver;

    @FXML
    private Button openSimpleDialogButton;
    @FXML
    private Button clearButton;

    @FXML
    private Button getSavedCart;
    @FXML
    private Button saveCart;

    public MainController(FxWeaver fxWeaver, SaleController saleController) {
        this.fxWeaver = fxWeaver;

        this.saleController = saleController;

    }

    @FXML
    public void initialize() {
        openSimpleDialogButton.setOnAction(
                actionEvent -> fxWeaver.loadController(AddItemDialogController.class).show()
        );
        fxWeaver.loadController(CustomerController.class).show();

        saleController.newSale();

        getSavedCart.setOnAction(actionEvent -> saleController.setSale(saleController.getSavedSale()));
        saveCart.setOnAction(actionEvent -> saleController.saveCurrentSale());


        clearButton.setOnAction((actionEvent -> saleController.newSale()));


    }


}
