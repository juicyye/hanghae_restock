package hanghae.restock.service;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.product.StockStatus;
import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;
import hanghae.restock.service.common.util.ErrorMessage;
import hanghae.restock.service.port.ProductNotificationHistoryRepository;
import hanghae.restock.service.port.ProductRepository;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final Map<Long, Product> productStatus = new ConcurrentHashMap<>();
    private final ProductRepository productRepository;
    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;

    /**
     * 리스탁을 실시한다
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Product handleRestock(Long productId) {
        Product product = getProductById(productId);
        product.addRestockPhase();
        productRepository.save(product);

        return product;
    }

    /**
     * 상품알림 정보를 DB에 저장한다
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addNotificationHistory(ProductNotificationHistory productNotificationHistory) {
        productNotificationHistoryRepository.save(productNotificationHistory);
    }

    /**
     * Product가 Cache에 없으면 DB에 가져와서 저장을 하고 있으면 그대로 반환한다
     */
    public Product getProductById(Long productId) {
        Product product = productStatus.get(productId);
        if (product == null) {
            product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
            productStatus.put(productId, product);
        }
        return product;
    }

    /**
     * StockStatus의 상태를 바꾼다
     */
    @Transactional
    public void changeProductStockStatus(Long productId, StockStatus stockStatus) {
        Product product = getProductById(productId);
        product.updateStockStatus(stockStatus);
        productRepository.save(product);
    }

    /**
     * 캐시에 저장된 Product를 지운다
     */
    public void clearData(Long productId) {
        productStatus.remove(productId);
    }

    @PostConstruct
    public void create() {
        productRepository.save(new Product(null, 0L, StockStatus.IN_STOCK));
    }
}
