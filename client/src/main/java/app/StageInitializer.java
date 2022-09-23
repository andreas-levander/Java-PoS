package app;

import app.controller.MainController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


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

        stage.setScene(new Scene(fxWeaver.loadView(MainController.class), 800, 600));
        stage.setTitle("Main/Admin Screen");
        //stage.setX(50);
        stage.show();
    }

}
