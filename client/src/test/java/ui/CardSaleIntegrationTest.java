package ui;

import app.AdminClientApplication;
import app.controller.cart.CartController;
import app.controller.sale.SaleController;
import app.javaFXclass;
import app.model.Item;
import app.model.payment.CardTransactionResult;
import app.service.PaymentService;
import app.service.StatisticService;
import javafx.stage.Stage;
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
import org.testfx.framework.junit5.Stop;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.timeout;

@SpringBootTest(classes = AdminClientApplication.class)
@ExtendWith(ApplicationExtension.class)
class CardSaleIntegrationTest {
    @Autowired
    private ApplicationContext applicationContext;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private StatisticService statisticService;

    @Start
    void start(Stage stage) {
        applicationContext.publishEvent(new javaFXclass.StageReadyEvent(stage));
        //add an item to the cart
        applicationContext.getBean(CartController.class).addToCart(new Item("test item", 1, "123", new ArrayList<>(), Money.of(2.0, "EUR")));

        Mockito.when(paymentService.getStatus()).thenReturn("DONE");

        Mockito.when(paymentService.getResult()).thenReturn(new CardTransactionResult(
                "123", "ACCEPTED", "123","ACCEPTED",
                "Debit", "1", "2022"));
    }

    @Stop
    void stop() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Test
    void buying_with_card_works(FxRobot robot) {
        robot.clickOn("#checkout");

        var cardBtn = robot.lookup("#cardButton").queryButton();
        Assertions.assertThat(cardBtn).isEnabled();

        var sale = applicationContext.getBean(SaleController.class).getCurrentSale();

        robot.clickOn(cardBtn);

        Mockito.verify(paymentService, timeout(2000).atLeastOnce()).getResult();

        var resultScreen = robot.lookup("#cardUiAnchorPane").query();
        Assertions.assertThat(resultScreen).isVisible();

        Mockito.verify(statisticService).saveSale(sale);

        var receiptBtn = robot.lookup("#receiptBtn").queryButton();

        Assertions.assertThat(receiptBtn).isEnabled();
    }
}
