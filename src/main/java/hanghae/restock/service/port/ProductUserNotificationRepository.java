package hanghae.restock.service.port;

import hanghae.restock.domain.productusernotification.ProductUserNotification;
import java.util.List;

public interface ProductUserNotificationRepository {
    void save(ProductUserNotification productUserNotification);

    List<ProductUserNotification> findAllProductNotiActive(Long productId);
}
