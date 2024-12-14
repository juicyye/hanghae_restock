package hanghae.restock.infrastructure.productusernotification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUserNotificationJpaRepository extends
        JpaRepository<ProductUserNotificationEntity, Long> {
}
