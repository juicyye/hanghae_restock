package hanghae.restock.infrastructure.productusernotificationhistory;

import hanghae.restock.domain.productusernotificationhistory.ProductUserNotificationHistory;
import hanghae.restock.service.port.ProductUserNotificationHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationHistoryRepositoryImpl implements ProductUserNotificationHistoryRepository {
    private final ProductUserNotificationHistoryJpaRepository jpaRepository;

    @Override
    public void save(List<ProductUserNotificationHistory> histories) {
        List<ProductUserNotificationHistoryEntity> entities = histories.stream()
                .map(ProductUserNotificationHistoryEntity::fromModel).toList();
        jpaRepository.saveAll(entities);
    }
}
