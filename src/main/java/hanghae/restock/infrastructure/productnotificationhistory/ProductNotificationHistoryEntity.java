package hanghae.restock.infrastructure.productnotificationhistory;

import hanghae.restock.domain.productnotificationhistory.RestockNotificationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "product_notification_history")
public class ProductNotificationHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long restockPhase;
    @Enumerated(EnumType.STRING)
    private RestockNotificationStatus restockNotificationStatus;
    private Long lastNotificationUserId;
}
