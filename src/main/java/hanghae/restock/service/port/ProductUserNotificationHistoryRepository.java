package hanghae.restock.service.port;

import hanghae.restock.domain.productusernotificationhistory.ProductUserNotificationHistory;
import java.util.List;

public interface ProductUserNotificationHistoryRepository {

    void save(List<ProductUserNotificationHistory> productUserNotificationHistory);
}
