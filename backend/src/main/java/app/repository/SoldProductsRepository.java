package app.repository;

import app.model.ProductStat;
import app.model.tables.SoldProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface SoldProductsRepository extends JpaRepository<SoldProduct, Integer> {
    @Query(value = "SELECT new app.model.ProductStat(s.name, s.barCode, count(s)) " +
            "FROM SoldProduct s " +
            "WHERE s.date BETWEEN :start AND :end " +
            "group by s.name, s.barCode " +
            "order by count(s) " +
            "DESC"
            )
    List<ProductStat> findTopProducts(Date start, Date end, Pageable page);

    @Query(value = "SELECT new app.model.ProductStat(s.name, s.barCode, count(s)) " +
                    "FROM SoldProduct s " +
                    "WHERE s.date BETWEEN :start AND :end " +
                    "group by s.name, s.barCode " +
                    "order by count(s) " +
                    "ASC "
    )
    List<ProductStat> findWorstProducts(Date start, Date end, Pageable page);

    @Query(value =  "SELECT new app.model.ProductStat(cast(s.date as date), count(s)) "+
                    "FROM SoldProduct s " +
                    "WHERE s.barCode = :barCode " +
                    "GROUP BY cast(s.date as date) " +
                    "order by cast(s.date as date)"
    )
    List<ProductStat> findProductStats(String barCode);

    @Query(value =  "SELECT new app.model.ProductStat(s.name, s.barCode, count(s)) "+
            "FROM SoldProduct s " +
            "WHERE s.bonusCustomerId = :id " +
            "GROUP BY s.name, s.barCode " +
            "order by count(s) " +
            "DESC "
    )
    List<ProductStat> findCustomerStats(Integer id);

}
