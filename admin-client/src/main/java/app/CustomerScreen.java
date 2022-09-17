package app;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CustomerScreen.fxml")
public class CustomerScreen {
    private Stage stage;

    @FXML
    private Pane CustomerVBox;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(CustomerVBox));

    }

    public void show() {
        stage.show();
    }
}
