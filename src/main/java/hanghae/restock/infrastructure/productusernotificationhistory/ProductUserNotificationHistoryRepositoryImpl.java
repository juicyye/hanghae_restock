package hanghae.restock.infrastructure.productusernotificationhistory;

import hanghae.restock.domain.productusernotificationhistory.ProductUserNotificationHistory;
import hanghae.restock.service.port.ProductUserNotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationHistoryRepositoryImpl implements ProductUserNotificationHistoryRepository {
    private final ProductUserNotificationHistoryJpaRepository jpaRepository;

    @Override
    public void save(ProductUserNotificationHistory domain) {
        jpaRepository.save(ProductUserNotificationHistoryEntity.fromModel(domain));
    }
}
