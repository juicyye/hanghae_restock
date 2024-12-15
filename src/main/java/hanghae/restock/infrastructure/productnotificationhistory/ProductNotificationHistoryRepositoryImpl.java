package hanghae.restock.infrastructure.productnotificationhistory;

import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;
import hanghae.restock.service.port.ProductNotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductNotificationHistoryRepositoryImpl implements ProductNotificationHistoryRepository {
    private final ProductNotificationHistoryJpaRepository jpaRepository;

    @Override
    public void save(ProductNotificationHistory domain) {
        ProductNotificationHistoryEntity entity = ProductNotificationHistoryEntity
                .create(domain);
        jpaRepository.save(entity);
    }
}
