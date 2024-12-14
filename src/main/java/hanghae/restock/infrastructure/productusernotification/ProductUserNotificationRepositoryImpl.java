package hanghae.restock.infrastructure.productusernotification;

import hanghae.restock.domain.productusernotification.ProductUserNotification;
import hanghae.restock.service.port.ProductUserNotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationRepositoryImpl implements ProductUserNotificationRepository {
    private final ProductUserNotificationJpaRepository jpaRepository;

    @Override
    public void save(ProductUserNotification model) {
        jpaRepository.save(ProductUserNotificationEntity.fromModel(model));
    }

    @Override
    public List<ProductUserNotification> findAllProductNotiActive(Long productId) {
        return jpaRepository.findActiveNotifiedUsersForProduct(productId).stream()
                .map(ProductUserNotificationEntity::toModel).toList();
    }

}
