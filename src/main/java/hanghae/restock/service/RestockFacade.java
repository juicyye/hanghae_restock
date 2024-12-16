package hanghae.restock.service;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;
import hanghae.restock.domain.productnotificationhistory.RestockNotificationStatus;
import hanghae.restock.service.common.util.NotificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestockFacade {

    private final Map<Long, ProductNotificationHistory> history = new ConcurrentHashMap<>();
    private final ProductService productService;
    private final UserNotificationService userNotificationService;


    /**
     * 리스탁을 진행한다
     */
    public void handleRestockProcess(Long productId) {
        Product product = productService.handleRestock(productId);
        Long restockPhase = product.getRestockPhase();

        history.computeIfAbsent(productId,
                k -> ProductNotificationHistory.create(productId, restockPhase, RestockNotificationStatus.IN_PROGRESS));

        executeUserNotification(productId, restockPhase);
    }

    /**
     * 유저에게 알림을 보낸다
     */
    private void executeUserNotification(Long productId, Long restockPhase) {
        Long lastUserId = null;
        ProductNotificationHistory productNotificationHistory = history.get(productId);

        try {
            Product product = productService.getProductById(productId);
            userNotificationService.processNotification(product, 0L);
            lastUserId = userNotificationService.getLastNotificationUserId(restockPhase);
            productNotificationHistory.updateNotification(RestockNotificationStatus.COMPLETED, lastUserId);
        } catch (NotificationException e) {
            productNotificationHistory.updateNotification(RestockNotificationStatus.CANCELED_BY_SOLD_OUT, lastUserId);
        } catch (IllegalArgumentException e) {
            handleNotificationError(productNotificationHistory, lastUserId);
        } finally {
            addToDBAndClear(productId, productNotificationHistory);
        }

    }

    /**
     * todo 알림 에러 발생 시 처리 로직
     */

    private void handleNotificationError(ProductNotificationHistory productNotificationHistory, Long lastUserId) {
        productNotificationHistory.updateNotification(RestockNotificationStatus.CANCELED_BY_ERROR, lastUserId);
    }

    /**
     * 알림 정보들을 DB에 저장하고 캐시삭제
     */
    private void addToDBAndClear(Long productId, ProductNotificationHistory productNotificationHistory) {
        productService.addNotificationHistory(productNotificationHistory);
        clearData(productId);
    }

    /**
     * 캐시에 저장된 데이터를 지운다
     */

    private void clearData(Long productId) {
        productService.clearData(productId);
        history.remove(productId);
    }


}
