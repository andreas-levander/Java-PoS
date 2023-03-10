package ui;

import app.AdminClientApplication;
import app.javaFXclass;
import app.model.Item;
import app.service.ItemService;
import javafx.scene.control.Label;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.stage.Stage;
import org.testfx.framework.junit5.Stop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@SpringBootTest(classes = AdminClientApplication.class)
@ExtendWith(ApplicationExtension.class)
class CartIntegrationTest {
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
        Mockito.when(itemService.getByBarcode(Mockito.anyString())).thenAnswer(i ->
        {
            var arg = i.getArguments()[0].toString();
            return Optional.of(new ArrayList<>((List.of(new Item(arg, 1, arg, new ArrayList<>(), Money.of(2.0, "EUR"))))));
        });

        applicationContext.publishEvent(new javaFXclass.StageReadyEvent(stage));
    }

    @Stop
    void stop() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void main_and_customer_screen_visible(FxRobot robot) {
        var mainScreen = robot.lookup("#mainPane").query();
        Assertions.assertThat(mainScreen).isVisible();

        var customerScreen = robot.lookup("#customerPane").query();
        Assertions.assertThat(customerScreen).isVisible();

    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void product_added_with_addProduct_dialog_is_shown_in_GUI(FxRobot robot) {
        // when:
        robot.clickOn("#addItemBtn");
        //add item dialg is visible
        var test = robot.lookup("#dialog").query();
        Assertions.assertThat(test).isVisible();

        robot.clickOn("#textField").write("123");
        robot.clickOn("#search");

        var searchList = robot.lookup("#searchList").queryListView();
        Assertions.assertThat(searchList.getItems()).isNotEmpty();
        searchList.getSelectionModel().selectFirst();
        robot.clickOn("#addToCart");

        //then item is shown in cashier cart
        var cashierCart = robot.lookup("#cashierCartListView").queryListView();
        Assertions.assertThat(cashierCart.getItems()).isNotEmpty();
        var addedObject = cashierCart.getItems().get(0);
        Assertions.assertThat(addedObject).isInstanceOf(Item.class);
        var addedItem = (Item) addedObject;
        Assertions.assertThat(addedItem.getBarCode()).isEqualTo("123");

        //cashier total price is updated
        var cashierTotalPrice = robot.lookup("#cashierTotalPrice").query();
        Assertions.assertThat(cashierTotalPrice).isInstanceOf(Label.class);
        var cashierTotalPriceLabel = (Label) cashierTotalPrice;
        Assertions.assertThat(cashierTotalPriceLabel).hasText("2.0");

        //then item is shown in customer cart
        var customerCart = robot.lookup("#customerListView").queryListView();
        Assertions.assertThat(customerCart.getItems()).isNotEmpty();
        var customerAddedObject = customerCart.getItems().get(0);
        Assertions.assertThat(customerAddedObject).isInstanceOf(Item.class);
        var customerAddedItem = (Item) customerAddedObject;
        Assertions.assertThat(customerAddedItem.getBarCode()).isEqualTo("123");

        //customer total price is updated
        var customerTotalPrice = robot.lookup("#customerTotalPrice").query();
        Assertions.assertThat(customerTotalPrice).isInstanceOf(Label.class);
        var customerTotalPriceLabel = (Label) customerTotalPrice;
        Assertions.assertThat(customerTotalPriceLabel).hasText("2.0");
    }
}
