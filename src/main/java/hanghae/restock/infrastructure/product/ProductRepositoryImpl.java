package hanghae.restock.infrastructure.product;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.product.StockStatus;
import hanghae.restock.service.port.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository jpaRepository;

    @Override
    public void save(Product product) {
        jpaRepository.save(ProductEntity.fromModel(product));
    }

    @Override
    public void processRestock(Long productId, StockStatus stockStatus, int restockPhase) {
        jpaRepository.updateRestockData(productId, stockStatus, restockPhase);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return jpaRepository.findById(productId).map(ProductEntity::toModel);
    }
}
