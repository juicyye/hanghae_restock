package hanghae.restock.infrastructure.productusernotification;

import hanghae.restock.domain.productusernotification.ActiveStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
@Table(name = "product_user_notification")
public class ProductUserNotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
