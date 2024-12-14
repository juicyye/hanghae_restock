package hanghae.restock.infrastructure.product;

import hanghae.restock.domain.product.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    @Query("update ProductEntity pe set pe.restockPhase = :restockPhase, pe.stockStatus = :stockStatus "
            + "where pe.id = :id")
    void updateRestockData(@Param("id") Long productId, @Param("stockStatus") StockStatus stockStatus,
                           @Param("restockPhase") int restockPhase);
}
