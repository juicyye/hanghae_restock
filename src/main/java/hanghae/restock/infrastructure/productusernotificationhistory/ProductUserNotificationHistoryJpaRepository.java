package hanghae.restock.infrastructure.productusernotificationhistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUserNotificationHistoryJpaRepository extends
        JpaRepository<ProductUserNotificationHistoryEntity, Long> {
}
