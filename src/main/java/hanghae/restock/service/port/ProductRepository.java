package hanghae.restock.service.port;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.product.StockStatus;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product);

    Optional<Product> findById(Long productId);
}
