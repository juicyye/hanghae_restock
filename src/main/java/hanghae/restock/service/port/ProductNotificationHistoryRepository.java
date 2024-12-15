package hanghae.restock.service.port;

import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;

public interface ProductNotificationHistoryRepository {

    void save(ProductNotificationHistory productNotificationHistory);
}
