package hanghae.restock.infrastructure.productusernotificationhistory;

import hanghae.restock.domain.productusernotificationhistory.ProductUserNotificationHistory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_user_notification_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductUserNotificationHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId;
    private Long restockPhase;
    private LocalDateTime notificationDate;

    public static ProductUserNotificationHistoryEntity fromModel(ProductUserNotificationHistory domain) {
        return new ProductUserNotificationHistoryEntity(
                domain.getId(), domain.getProductId(), domain.getUserId(), domain.getRestockPhase(),
                domain.getNotificationDate()
        );
    }

    public ProductUserNotificationHistory toModel() {
        return new ProductUserNotificationHistory(
                id, productId, userId, restockPhase, notificationDate
        );
    }
}
