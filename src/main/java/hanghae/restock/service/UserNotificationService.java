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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserNotificationService {
    private final Map<Long, List<ProductUserNotificationHistory>> history = new ConcurrentHashMap<>();
    private final UserService userService;
    private final LocalDateTimeHolder localDateTimeHolder;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;

    /**
     * 마지막으로 저장한 유저 Id를 반환한다
     */
    public Long getLastNotificationUserId(Long restockPhase) {
        return Objects.requireNonNull(Objects.requireNonNull(history.get(restockPhase)
                .stream()
                .max(Comparator.comparing(ProductUserNotificationHistory::getNotificationDate))
                .map(ProductUserNotificationHistory::getUserId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_USER_NOTI_HISTORY.getMessage())))
        );
    }

    /**
     * 유저 알림 정보에 알림들을 저장한다
     */
    public void processNotification(Product product) {
        Long restockPhase = product.getRestockPhase();
        Long productId = product.getId();
        List<ProductUserNotification> users = userService.getActiveProductNotifiers(productId);

        for (ProductUserNotification user : users) {
            if (product.availableStockNotification()) {
                addRestockHistory(user, restockPhase, productId);
            } else {
                throw new NotificationException(ErrorMessage.OUT_OF_STOCK.getMessage());
            }
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRestockHistory(Long stockPhase) {
        List<ProductUserNotificationHistory> histories = history.get(stockPhase);
        productUserNotificationHistoryRepository.save(histories);
        history.remove(stockPhase);
    }

}
