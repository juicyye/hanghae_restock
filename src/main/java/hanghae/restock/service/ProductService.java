package hanghae.restock.service;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.product.StockStatus;
import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;
import hanghae.restock.service.common.util.ErrorMessage;
import hanghae.restock.service.port.ProductNotificationHistoryRepository;
import hanghae.restock.service.port.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;

    /**
     * 리스탁을 실시한다
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Product handleRestock(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
        product.addRestockPhase();
        productRepository.save(product);

        return product;
    }

    /**
     * 상품과 상품알림 정보를 DB에 저장한다
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addNotificationHistory(ProductNotificationHistory productNotificationHistory) {
        productNotificationHistoryRepository.save(productNotificationHistory);
    }

    @PostConstruct
    public void create() {
        productRepository.save(new Product(null, 0L, StockStatus.IN_STOCK));
    }

}
