package hanghae.restock.mock;

import hanghae.restock.domain.productusernotification.ActiveStatus;
import hanghae.restock.domain.productusernotification.ProductUserNotification;
import hanghae.restock.service.port.ProductUserNotificationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeProductUserNotificationRepository implements ProductUserNotificationRepository {
    private List<ProductUserNotification> data = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @Override
    public void save(ProductUserNotification domain) {
        if(domain.getId() == null || domain.getId().equals(0L)) {
            ProductUserNotification newDomain = new ProductUserNotification(
                    counter.getAndIncrement(), domain.getProductId(), domain.getUserId(),
                    domain.getActiveStatus(), domain.getCreatedAt(), domain.getUpdatedAt()
            );
            data.add(newDomain);
        } else{
            data.removeIf(i -> Objects.equals(domain.getId(), i.getId()));
            data.add(domain);
        }

    }

    @Override
    public List<ProductUserNotification> findAllProductNotification(Long productId, long cursor, int size,
                                                                    ActiveStatus activeStatus) {
        return data.stream()
                .filter(i -> i.getProductId().equals(productId))
                .filter(i -> i.getActiveStatus().equals(ActiveStatus.ACTIVATE))
                .filter(i -> i.getId() <= cursor)
                .limit(size)
                .toList();
    }

}
