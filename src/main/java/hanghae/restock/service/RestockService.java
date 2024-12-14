package hanghae.restock.service;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;
import hanghae.restock.domain.productnotificationhistory.RestockNotificationStatus;
import hanghae.restock.service.common.util.ErrorMessage;
import hanghae.restock.service.port.ProductRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestockService {
    private final Map<Long, ProductNotificationHistory> history = new ConcurrentHashMap<>();
    private final ProductRepository productRepository;

    public Long handleRestock(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
        Long restockPhase = product.getRestockPhase() + 1;
        history.computeIfAbsent(restockPhase,
                k -> ProductNotificationHistory.create(productId, restockPhase, RestockNotificationStatus.IN_PROGRESS));
        return restockPhase;
    }

}
