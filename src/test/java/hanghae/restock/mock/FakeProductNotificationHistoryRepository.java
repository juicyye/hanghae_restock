package hanghae.restock.mock;

import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;
import hanghae.restock.service.port.ProductNotificationHistoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeProductNotificationHistoryRepository implements ProductNotificationHistoryRepository {
    private List<ProductNotificationHistory> data = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @Override
    public void save(ProductNotificationHistory domain) {
        if (domain.getId() == null || domain.getId().equals(0L)) {
            ProductNotificationHistory newDomain = new ProductNotificationHistory(
                    counter.getAndIncrement(), domain.getProductId(), domain.getRestockPhase(),
                    domain.getRestockNotificationStatus(), domain.getLastNotificationUserId()
            );
            data.add(newDomain);
        } else {
            data.removeIf(i -> Objects.equals(domain.getId(), i.getId()));
            data.add(domain);
        }
    }

    public ProductNotificationHistory findById(Long id) {
        return data.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);
    }
}
