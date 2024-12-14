package hanghae.restock.service.port;

import hanghae.restock.domain.productusernotificationhistory.ProductUserNotificationHistory;

public interface ProductUserNotificationHistoryRepository {

    void save(ProductUserNotificationHistory productUserNotificationHistory);
}
