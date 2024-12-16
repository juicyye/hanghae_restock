package hanghae.restock.mock;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.product.StockStatus;
import hanghae.restock.domain.productusernotificationhistory.ProductUserNotificationHistory;
import hanghae.restock.service.port.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeProductRepository implements ProductRepository {
    private List<Product> data = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();


    @Override
    public void save(Product domain) {
        if (domain.getId() == null || domain.getId().equals(0L)) {
            Product newDomain = new Product(
                    counter.getAndIncrement(), domain.getRestockPhase(), domain.getStockStatus()
            );
            data.add(newDomain);
        } else {
            data.removeIf(i -> Objects.equals(domain.getId(), i.getId()));
            data.add(domain);
        }
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return data.stream().filter(i -> Objects.equals(i.getId(), productId)).findFirst();
    }
}
