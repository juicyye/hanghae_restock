package hanghae.restock.service;

import hanghae.restock.domain.productusernotification.ProductUserNotification;
import hanghae.restock.domain.productusernotificationhistory.ProductUserNotificationHistory;
import hanghae.restock.service.port.LocalDateTimeHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationService {
    private final Map<Long, List<ProductUserNotificationHistory>> history = new ConcurrentHashMap<>();
    private final LocalDateTimeHolder localDateTimeHolder;
    private final UserService userService;

    public void processNotification(Long productId, Long restockPhase) {
        List<ProductUserNotification> users = userService.getActiveProductNotifiers(productId);
        users.forEach(i -> history.computeIfAbsent(restockPhase, k ->
                new ArrayList<>()).add(createHistory(productId, i.getUserId(), restockPhase)));
    }

    private ProductUserNotificationHistory createHistory(Long productId, Long userId, Long restockPhase) {
        return ProductUserNotificationHistory.create(
                productId, userId, restockPhase, localDateTimeHolder.getCurrentDate()
        );
    }

    public Long getLastNotificationUserId(Long productId, Long restockPhase) {
        return Objects.requireNonNull(history.get(restockPhase)
                        .stream()
                        .max((a, b) -> b.getNotificationDate().compareTo(a.getNotificationDate()))
                        .orElse(null))
                .getUserId();
    }

}
