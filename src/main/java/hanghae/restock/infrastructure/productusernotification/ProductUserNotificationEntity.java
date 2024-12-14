package hanghae.restock.infrastructure.productusernotification;

import hanghae.restock.domain.productusernotification.ActiveStatus;
import hanghae.restock.domain.productusernotification.ProductUserNotification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_user_notification")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductUserNotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false)
    private Long userId;
    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductUserNotificationEntity fromModel(ProductUserNotification model) {
        return new ProductUserNotificationEntity(
                model.getId(), model.getProductId(), model.getUserId(), model.getActiveStatus(), model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }

    public ProductUserNotification toModel(){
        return new ProductUserNotification(
                id, productId, userId, activeStatus, createdAt, updatedAt
        );
    }
}
