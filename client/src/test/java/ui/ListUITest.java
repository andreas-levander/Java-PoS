package ui;

import app.AdminClientApplication;
import app.controller.MainController;
import net.rgielen.fxweaver.core.FxWeaver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/* Not yet Implemented, JUnit5AssertJ */
@SpringBootTest(classes = AdminClientApplication.class)
@ExtendWith(ApplicationExtension.class)
class ListUITest {
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * Will be called with {@code @Before} semantics, i.e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        stage.setScene(new Scene(fxWeaver.loadView(MainController.class), 600, 400));
        stage.show();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void should_contain_button_with_text(FxRobot robot) {
        //Assertions.assertThat(button).hasText("click me!");
        // or (lookup by css id):
        //Assertions.assertThat(robot.lookup("#myButton").queryAs(Button.class)).hasText("click me!");
        // or (lookup by css class):
        //Assertions.assertThat(robot.lookup(".button").queryAs(Button.class)).hasText("click me!");
        // or (query specific type):
        //Assertions.assertThat(robot.lookup(".clearButton").queryButton()).hasText("clear");

        var test = robot.lookup("#mainVbox").query();
        System.out.println(test);
        Button test2 = (Button) test.lookup("#clearButton");
        System.out.println(test2);
        Assertions.assertThat(test2).hasText("Clear");


    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    @Disabled
    void when_button_is_clicked_text_changes(FxRobot robot) {
        // when:
        robot.clickOn(".button");

        // then:
        //Assertions.assertThat(button).hasText("clicked!");
        // or (lookup by css id):
        Assertions.assertThat(robot.lookup("#myButton").queryAs(Button.class)).hasText("clicked!");
        // or (lookup by css class):
        Assertions.assertThat(robot.lookup(".button").queryAs(Button.class)).hasText("clicked!");
        // or (query specific type)
        Assertions.assertThat(robot.lookup(".button").queryButton()).hasText("clicked!");
    }
}
