package app;

import app.model.tables.SoldProduct;
import app.repository.PriceRepository;
import app.repository.SoldProductsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@DataJpaTest
public class SoldProductsRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private SoldProductsRepository repository;

    private final Date dateSold = new Date();

    @BeforeEach
    void setUp() {
        repository.saveAll(List.of(
                new SoldProduct(1,1,"123","testProduct", dateSold),
                new SoldProduct(1,1,"123","testProduct", dateSold),
                new SoldProduct(2,1,"1234","testProduct2", dateSold),
                new SoldProduct(2,1,"1234","testProduct2", dateSold),
                new SoldProduct(2,1,"1234","testProduct2", dateSold),
                new SoldProduct(3,1,"12345","testProduct3", dateSold)
        ));
    }

    @Test
    public void top_sold_is_correct() {
        // given
        var today = LocalDate.now();
        var result = repository.findTopProducts(java.sql.Date.valueOf(today),
                java.sql.Date.valueOf(today.plusDays(1)), PageRequest.of(0, 3));

        var firstResult = result.get(0);
        var secondResult = result.get(1);
        Assertions.assertThat(firstResult.getName()).isEqualTo("testProduct2");
        Assertions.assertThat(firstResult.getSold()).isEqualTo(3L);

        Assertions.assertThat(secondResult.getName()).isEqualTo("testProduct");
        Assertions.assertThat(secondResult.getSold()).isEqualTo(2L);

    }

    @Test
    public void date_range_works() {
        // given
        var today = LocalDate.now();
        var result = repository.findTopProducts(java.sql.Date.valueOf(today.minusDays(20)),
                java.sql.Date.valueOf(today.minusDays(10)), PageRequest.of(0, 3));

        Assertions.assertThat(result).isEmpty();

    }

    @Test
    public void least_sold_is_correct() {
        // given
        var today = LocalDate.now();
        var result = repository.findWorstProducts(java.sql.Date.valueOf(today.minusDays(20)),
                java.sql.Date.valueOf(today.plusDays(1)), PageRequest.of(0, 3));

        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0).getSold()).isEqualTo(1L);
        Assertions.assertThat(result.get(0).getName()).isEqualTo("testProduct3");

        Assertions.assertThat(result.get(1).getSold()).isEqualTo(2L);
        Assertions.assertThat(result.get(1).getName()).isEqualTo("testProduct");

    }

    @Test
    public void stats_on_single_product_returns_sold_per_day() {
        // given
        var today = LocalDate.now();
        var result = repository.findProductStats("123");

        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0).getSold()).isEqualTo(2L);
        Assertions.assertThat(result.get(0).getDate()).isEqualTo(java.sql.Date.valueOf(today));

    }

    @Test
    public void customerStats_returns_bought_products() {
        // given
        var result = repository.findCustomerStats(1);

        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0).getName()).isEqualTo("testProduct2");
        Assertions.assertThat(result.get(0).getSold()).isEqualTo(3L);

        Assertions.assertThat(result.get(1).getName()).isEqualTo("testProduct");
        Assertions.assertThat(result.get(1).getSold()).isEqualTo(2L);

        Assertions.assertThat(result.get(2).getName()).isEqualTo("testProduct3");
        Assertions.assertThat(result.get(2).getSold()).isEqualTo(1L);

    }
}
