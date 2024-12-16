package hanghae.restock.service.port;

import hanghae.restock.domain.productusernotification.ActiveStatus;
import hanghae.restock.domain.productusernotification.ProductUserNotification;
import java.util.List;

public interface ProductUserNotificationRepository {
    void save(ProductUserNotification productUserNotification);

    List<ProductUserNotification> findAllProductNotification(Long productId, long cursor, int size, ActiveStatus activeStatus);
}
