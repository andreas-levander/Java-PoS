package app;

import app.model.tables.Bonus;
import app.repository.BonusRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class BonusRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BonusRepository repository;

    @Test
    public void bonus_customer_can_be_saved() {
        // given
        Bonus bonus1 = new Bonus(1,50L);
        repository.save(bonus1);

        var response = repository.findById(1);
        Assertions.assertThat(response.isPresent()).isTrue();
        Assertions.assertThat(response.get().getCustomerId()).isEqualTo(1);
        Assertions.assertThat(response.get().getBonusPoints()).isEqualTo(50L);

    }

    @Test
    public void use_bonus_updates_totalBonus() {
        // given
        Bonus bonus1 = new Bonus(1,50L);
        repository.save(bonus1);

        repository.useBonus(1,30L);
        entityManager.clear();
        //entityManager.refresh(entityManager.find(Bonus.class, 2));

        var response = repository.findById(1);
        Assertions.assertThat(response.isPresent()).isTrue();
        Assertions.assertThat(response.get().getCustomerId()).isEqualTo(1);
        Assertions.assertThat(response.get().getBonusPoints()).isEqualTo(20L);
    }

}
