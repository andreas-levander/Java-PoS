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
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;

import static org.mockito.Mockito.timeout;

@SpringBootTest(classes = AdminClientApplication.class)
@ExtendWith(ApplicationExtension.class)
class CashSaleIntegrationTest {
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

    @Test
    void buying_with_cash_works(FxRobot robot) {
        robot.clickOn("#checkout");

        var cashBtn = robot.lookup("#cashButton").queryButton();
        Assertions.assertThat(cashBtn).isEnabled();

        var sale = applicationContext.getBean(SaleController.class).getCurrentSale();

        robot.clickOn(cashBtn);

        Mockito.verify(paymentService).openCashBox();

        var cashDialog = robot.lookup("#cashDialog").query();
        Assertions.assertThat(cashDialog).isVisible();

        robot.clickOn("#amountReceivedField");
        robot.write("20");
        robot.clickOn("#confirmButton");

        var change = robot.lookup("#changeLabel").queryLabeled();
        Assertions.assertThat(change).hasText("18.0");

        var total = robot.lookup("#cashTotalLabel").queryLabeled();
        Assertions.assertThat(total).hasText("2.0");

        Mockito.verify(statisticService).saveSale(sale);

        var receiptBtn = robot.lookup("#receiptBtn").queryButton();

        Assertions.assertThat(receiptBtn).isEnabled();
    }
}
