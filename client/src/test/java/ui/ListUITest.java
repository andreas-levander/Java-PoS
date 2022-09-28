package ui;

import app.AdminClientApplication;
import app.controller.MainController;
import app.model.Item;
import app.service.ItemService;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;

/* Not yet Implemented, JUnit5AssertJ */
@SpringBootTest(classes = AdminClientApplication.class)
@ExtendWith(ApplicationExtension.class)
class ListUITest {
    @Autowired
    private ApplicationContext applicationContext;
    @MockBean
    private ItemService itemService;


    /**
     * Will be called with {@code @Before} semantics, i.e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        Mockito.when(itemService.getByBarcodeForTest(Mockito.anyString())).thenAnswer(i ->
        {
            var arg = i.getArguments()[0].toString();
            return new Item(arg, arg, arg, new ArrayList<>(), 2.0);
        });
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        stage.setScene(new Scene(fxWeaver.loadView(MainController.class), 600, 400));
        stage.show();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void main_and_customer_screen_visible(FxRobot robot) {
        //Assertions.assertThat(button).hasText("click me!");
        // or (lookup by css id):
        //Assertions.assertThat(robot.lookup("#myButton").queryAs(Button.class)).hasText("click me!");
        // or (lookup by css class):
        //Assertions.assertThat(robot.lookup(".button").queryAs(Button.class)).hasText("click me!");
        // or (query specific type):
        //Assertions.assertThat(robot.lookup(".clearButton").queryButton()).hasText("clear");

        var mainScreen = robot.lookup("#mainVbox").query();
        Assertions.assertThat(mainScreen).isVisible();

        var customerScreen = robot.lookup("#customerVBox").query();
        Assertions.assertThat(customerScreen).isVisible();


    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void when_addProduct_button_is_clicked_new_dialog_opens(FxRobot robot) {
        // when:
        robot.clickOn("#addItemByBarcode");

        // then:
        var test = robot.lookup("#dialog").query();
        System.out.println(test);
        Assertions.assertThat(test).isVisible();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void product_added_with_addProduct_dialog_is_shown_in_GUI(FxRobot robot) {
        // when:
        robot.clickOn("#addItemByBarcode");
        robot.clickOn("#textField").write("123");
        robot.clickOn("#addItem");

        //then item is shown in cashier cart
        var cashierCart = robot.lookup("#cashierCartListView").query();
        Assertions.assertThat(cashierCart).isInstanceOf(ListView.class);
        var listView = (ListView<Item>) cashierCart;
        Assertions.assertThat(listView.getItems()).isNotEmpty();
        var addedItem = listView.getItems().get(0);
        Assertions.assertThat(addedItem).isInstanceOf(Item.class);
        Assertions.assertThat(addedItem.getBarCode()).isEqualTo("123");

        //cashier total price is updated
        var cashierTotalPrice = robot.lookup("#cashierTotalPrice").query();
        Assertions.assertThat(cashierTotalPrice).isInstanceOf(Label.class);
        var cashierTotalPriceLabel = (Label) cashierTotalPrice;
        Assertions.assertThat(cashierTotalPriceLabel).hasText("2.0");

        //then item is shown in customer cart
        var customerCart = robot.lookup("#customerListView").query();
        Assertions.assertThat(customerCart).isInstanceOf(ListView.class);
        var customerListView = (ListView<Item>) customerCart;
        Assertions.assertThat(customerListView.getItems()).isNotEmpty();
        var customerAddedItem = customerListView.getItems().get(0);
        Assertions.assertThat(customerAddedItem).isInstanceOf(Item.class);
        Assertions.assertThat(customerAddedItem.getBarCode()).isEqualTo("123");

        //customer total price is updated
        var customerTotalPrice = robot.lookup("#customerTotalPrice").query();
        Assertions.assertThat(customerTotalPrice).isInstanceOf(Label.class);
        var customerTotalPriceLabel = (Label) customerTotalPrice;
        Assertions.assertThat(customerTotalPriceLabel).hasText("2.0");
    }
}
