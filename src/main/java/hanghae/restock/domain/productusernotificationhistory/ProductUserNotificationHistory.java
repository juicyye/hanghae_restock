package hanghae.restock.domain.productusernotificationhistory;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductUserNotificationHistory implements Comparable<ProductUserNotificationHistory> {
    private Long id;
    private Long productId;
    private Long userId;
    private Long restockPhase;
    private LocalDateTime notificationDate;

    public static ProductUserNotificationHistory create(Long productId, Long userId, Long restockPhase,
                                                        LocalDateTime notificationDate) {
        return new ProductUserNotificationHistory(
                null, productId, userId, restockPhase, notificationDate
        );
    }

    @Override
    public int compareTo(ProductUserNotificationHistory o) {
        int dateComparison = notificationDate.compareTo(o.notificationDate);
        return dateComparison != 0 ? dateComparison : userId.compareTo(o.userId);
    }
}
