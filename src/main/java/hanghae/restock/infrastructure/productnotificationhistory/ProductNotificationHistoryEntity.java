package hanghae.restock.infrastructure.productnotificationhistory;

import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;
import hanghae.restock.domain.productnotificationhistory.RestockNotificationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_notification_history")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductNotificationHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long restockPhase;
    @Enumerated(EnumType.STRING)
    private RestockNotificationStatus restockNotificationStatus;
    private Long lastNotificationUserId;

    public static ProductNotificationHistoryEntity create(ProductNotificationHistory domain) {
        return new ProductNotificationHistoryEntity(
                domain.getId(), domain.getProductId(), domain.getRestockPhase(),
                domain.getRestockNotificationStatus(), domain.getLastNotificationUserId()
        );
    }
}
