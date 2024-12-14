package hanghae.restock.domain.productusernotification;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductUserNotification {
    private Long id;
    private Long productId;
    private Long userId;
    private ActiveStatus activeStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductUserNotification create(Long productId, Long userId,
                                                 ActiveStatus activeStatus,
                                                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ProductUserNotification(null, productId, userId, activeStatus, createdAt, updatedAt);

    }
}
