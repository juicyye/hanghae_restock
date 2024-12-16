package hanghae.restock.service;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;
import hanghae.restock.domain.productnotificationhistory.RestockNotificationStatus;
import hanghae.restock.service.common.util.NotificationException;
import hanghae.restock.service.port.ProductNotificationHistoryRepository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestockFacade {

    private final Map<Long, Product> productStatus = new ConcurrentHashMap<>();
    private final Map<Long, ProductNotificationHistory> history = new ConcurrentHashMap<>();
    private final ProductService productService;
    private final UserNotificationService userNotificationService;


    /**
     * 리스탁을 진행한다
     */
    public void handleRestockProcess(Long productId) {
        Product product = productService.handleRestock(productId);
        Long restockPhase = product.getRestockPhase();

        productStatus.put(restockPhase, product);
        history.computeIfAbsent(restockPhase,
                k -> ProductNotificationHistory.create(productId, restockPhase, RestockNotificationStatus.IN_PROGRESS));

        executeUserNotification(restockPhase);
    }

    /**
     * 유저에게 알림을 보낸다
     */
    private void executeUserNotification(Long restockPhase) {
        Long lastUserId = null;
        ProductNotificationHistory productNotificationHistory = history.get(restockPhase);

        try {
            userNotificationService.processNotification(productStatus.get(restockPhase));
            lastUserId = userNotificationService.getLastNotificationUserId(restockPhase);
            productNotificationHistory.updateNotification(RestockNotificationStatus.COMPLETED, lastUserId);
        } catch (NotificationException e) {
            productNotificationHistory.updateNotification(RestockNotificationStatus.CANCELED_BY_SOLD_OUT, lastUserId);
        } catch (IllegalArgumentException e) {
            productNotificationHistory.updateNotification(RestockNotificationStatus.CANCELED_BY_ERROR, lastUserId);
        } finally {
            productService.addNotificationHistory(productNotificationHistory);
            userNotificationService.saveRestockHistory(restockPhase);
            clearData(restockPhase);
        }

    }

    private void clearData(Long restockPhase) {
        productStatus.remove(restockPhase);
        history.remove(restockPhase);
    }


}
