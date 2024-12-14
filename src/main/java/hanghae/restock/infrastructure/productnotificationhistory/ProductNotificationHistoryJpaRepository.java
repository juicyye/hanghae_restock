package hanghae.restock.infrastructure.productnotificationhistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNotificationHistoryJpaRepository extends
        JpaRepository<ProductNotificationHistoryEntity, Long> {
}
