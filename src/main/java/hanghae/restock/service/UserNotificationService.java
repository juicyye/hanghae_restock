package hanghae.restock.service;

import static hanghae.restock.service.common.util.ErrorMessage.NOT_FOUND_USER_NOTI_HISTORY;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.productusernotification.ProductUserNotification;
import hanghae.restock.domain.productusernotificationhistory.ProductUserNotificationHistory;
import hanghae.restock.service.common.util.ErrorMessage;
import hanghae.restock.service.common.util.NotificationException;
import hanghae.restock.service.port.LocalDateTimeHolder;
import hanghae.restock.service.port.ProductUserNotificationHistoryRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserNotificationService {
    private static final int SIZE = 500;
    private final Map<Long, List<ProductUserNotificationHistory>> history = new ConcurrentHashMap<>();
    private final UserService userService;
    private final LocalDateTimeHolder localDateTimeHolder;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;

    /**
     * 마지막으로 저장한 유저 Id를 반환한다
     */
    public Long getLastNotificationUserId(Long productId) {
        return history.get(productId)
                .stream()
                .max(ProductUserNotificationHistory::compareTo)
                .map(ProductUserNotificationHistory::getUserId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_USER_NOTI_HISTORY.getMessage())
                );
    }

    /**
     * 유저 알림 정보에 알림들을 저장한다
     * cursor기반 페이지네이션, 가져온 페이지의 사이즈가 500이 되면 반복하고 안되면 안한다
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processNotification(Product product) {
        Long restockPhase = product.getRestockPhase();
        Long productId = product.getId();
        int size = SIZE;
        long cursor = 0;

        while (size == SIZE) {
            List<ProductUserNotification> users = userService.getActiveProductNotifiers(productId, cursor, SIZE);
            for (ProductUserNotification user : users) {
                if (product.availableStockNotification()) {
                    addRestockHistory(user, restockPhase, productId);
                } else {
                    throw new NotificationException(ErrorMessage.OUT_OF_STOCK.getMessage());
                }
            }

            saveRestockHistory(productId);
            size = users.size();
            cursor = users.get(size - 1).getId();
        }
    }

    private void addRestockHistory(ProductUserNotification user, Long restockPhase, Long productId) {
        history.computeIfAbsent(restockPhase, k ->
                new ArrayList<>()).add(createHistory(productId, user.getUserId(), restockPhase,
                localDateTimeHolder.getCurrentDate()));
    }

    /**
     * ProductUserNotificationHistory을 생성한다
     */
    private ProductUserNotificationHistory createHistory(Long productId, Long userId, Long restockPhase,
                                                         LocalDateTime mostRecentDate) {
        return ProductUserNotificationHistory.create(
                productId, userId, restockPhase, mostRecentDate
        );
    }

    /**
     * DB에 ProductUserNotificationHistory을 저장한다
     */

    public void saveRestockHistory(Long productId) {
        List<ProductUserNotificationHistory> histories = history.get(productId);
        if (histories == null || histories.isEmpty()) {
            return;
        }
        productUserNotificationHistoryRepository.save(histories);
        history.remove(productId);
    }

}
