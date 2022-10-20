package ui;

import app.AdminClientApplication;
import app.javaFXclass;
import app.model.ProductStatistic;
import app.service.StatisticService;
import javafx.stage.Stage;
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
import java.util.Date;
import java.util.List;


@SpringBootTest(classes = AdminClientApplication.class)
@ExtendWith(ApplicationExtension.class)
class MarketingIntegrationTest {
    @Autowired
    private ApplicationContext applicationContext;
    @MockBean
    private StatisticService statisticService;
    private ProductStatistic stat;

    @Start
    void start(Stage stage) {
        applicationContext.publishEvent(new javaFXclass.StageReadyEvent(stage));

        stat = new ProductStatistic(new Date(),"test", "123", 20L);
        Mockito.when(statisticService.getCustomerStats("1")).thenReturn(List.of(stat));
    }


    @Test
    void customer_stats_is_shown(FxRobot robot) {
        robot.clickOn("#marketingTab");

        var marketingUi = robot.lookup("#smBorderPane").query();
        Assertions.assertThat(marketingUi).isVisible();

        robot.clickOn("#customerIdTextBox");
        robot.write("1");
        robot.clickOn("#getCustomerStatsBtn");

        Mockito.verify(statisticService).getCustomerStats("1");

        var list = robot.lookup("#marketingList").queryListView();

        Assertions.assertThat(list).isNotEmpty();
        Assertions.assertThat(list.getItems().get(0)).isEqualTo(stat);
    }
}
