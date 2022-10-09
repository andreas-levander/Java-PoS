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
}