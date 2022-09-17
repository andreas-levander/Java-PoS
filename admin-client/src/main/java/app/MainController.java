package app;

import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.scene.control.Button;


@Component
@FxmlView("/MainController.fxml")
public class MainController {
    private final FxWeaver fxWeaver;

    private final CartModel cartModel;

    @FXML
    private Button openSimpleDialogButton;
    @FXML
    private Button clearButton;

    public MainController(FxWeaver fxWeaver, CartModel cartModel) {
        this.fxWeaver = fxWeaver;
        this.cartModel = cartModel;

    }

    @FXML
    public void initialize() {
        fxWeaver.loadController(CustomerScreen.class).show();

        openSimpleDialogButton.setOnAction(
                actionEvent -> fxWeaver.loadController(DialogController.class).show()
        );

        clearButton.setOnAction((actionEvent -> cartModel.clear()));
    }


}
