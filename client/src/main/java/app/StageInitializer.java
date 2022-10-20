package app;

import app.controller.CustomerController;
import app.controller.MainController;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/** Class responsible for showing the main UI on startup */
@Component
public class StageInitializer implements ApplicationListener<javaFXclass.StageReadyEvent> {
    private final FxWeaver fxWeaver;

    @Autowired
    public StageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(javaFXclass.StageReadyEvent event) {
        Stage stage = event.getStage();

        // load customer ui
        var customerController = fxWeaver.loadController(CustomerController.class);
        var mainController = fxWeaver.loadController(MainController.class);

        // load admin ui
        stage.setScene(mainController.getScene());
        stage.setTitle("Main/Admin Screen");

        // show uis
        customerController.show();
        stage.show();
    }

}
