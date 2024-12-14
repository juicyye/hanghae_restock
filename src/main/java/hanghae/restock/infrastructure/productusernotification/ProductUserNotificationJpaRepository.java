package hanghae.restock.infrastructure.productusernotification;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductUserNotificationJpaRepository extends
        JpaRepository<ProductUserNotificationEntity, Long> {

    @Query("select pun from ProductUserNotificationEntity pun where pun.productId = :productId and pun.activeStatus = 'ACTIVATE'")
    List<ProductUserNotificationEntity> findActiveNotifiedUsersForProduct(Long productId);
}
