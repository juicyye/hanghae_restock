package hanghae.restock.domain.productusernotification;

import java.time.LocalDateTime;

public class ProductUserNotification {
    private Long id;
    private Long productId;
    private Long userId;
    private ActiveStatus activeStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
