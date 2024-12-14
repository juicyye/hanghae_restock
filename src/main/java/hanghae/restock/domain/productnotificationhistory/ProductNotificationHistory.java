package hanghae.restock.domain.productnotificationhistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductNotificationHistory {
    private Long id;
    private Long productId;
    private Long restockPhase;
    private RestockNotificationStatus restockNotificationStatus;
    private Long lastNotificationUserId;

    public static ProductNotificationHistory create(Long productId, Long restockPhase,
                                                    RestockNotificationStatus status) {
        return new ProductNotificationHistory(
                null, productId, restockPhase, status, null
        );
    }

    public void updateNotification(RestockNotificationStatus status, Long lastNotificationUserId) {
        this.lastNotificationUserId = lastNotificationUserId;
        this.restockNotificationStatus = status;
    }
}
