package hanghae.restock.domain.productusernotificationhistory;

import java.time.LocalDateTime;

public class ProductUserNotificationHistory {
    private Long id;
    private Long productId;
    private Long userId;
    private Long restockPhase;
    private LocalDateTime notificationDate;
}
