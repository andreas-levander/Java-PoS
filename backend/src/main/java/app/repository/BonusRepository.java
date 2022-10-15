package app.repository;

import app.model.tables.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** Database methods for Bonus table  */
@Repository
public interface BonusRepository extends JpaRepository<Bonus, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bonus (customer_id, bonus_points) " +
            "VALUES (:id, :bonusPoints) " +
            "ON CONFLICT (customer_id) DO UPDATE SET bonus_points = bonus.bonus_points + :bonusPoints"
            , nativeQuery = true)
    void updateBonus(Integer id, Long bonusPoints);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Bonus " +
            "SET bonusPoints = bonusPoints - :bonusPoints " +
            "WHERE customerId = :id"
            )
    void useBonus(Integer id, Long bonusPoints);
}
