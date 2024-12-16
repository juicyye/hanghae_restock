package hanghae.restock.mock;

import hanghae.restock.domain.productusernotificationhistory.ProductUserNotificationHistory;
import hanghae.restock.service.port.ProductUserNotificationHistoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeProductUserNotificationHistoryRepository implements ProductUserNotificationHistoryRepository {
    private List<ProductUserNotificationHistory> data = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @Override
    public void save(ProductUserNotificationHistory domain) {
        if (domain.getId() == null || domain.getId().equals(0L)) {
            ProductUserNotificationHistory newDomain = new ProductUserNotificationHistory(
                    counter.getAndIncrement(), domain.getProductId(), domain.getUserId(),
                    domain.getRestockPhase(), domain.getNotificationDate()
            );
            data.add(newDomain);
        } else {
            data.removeIf(i -> Objects.equals(domain.getId(), i.getId()));
            data.add(domain);
        }
    }

    public List<ProductUserNotificationHistory> getData() {
        return data;
    }


}
