package hanghae.restock.service;

import hanghae.restock.domain.productusernotification.ActiveStatus;
import hanghae.restock.domain.productusernotification.ProductUserNotification;
import hanghae.restock.service.port.LocalDateTimeHolder;
import hanghae.restock.service.port.ProductUserNotificationRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ProductUserNotificationRepository productUserNotificationRepository;
    private final LocalDateTimeHolder localDateTimeHolder;

    public void addNotification(Long productId, Long userId) {
        LocalDateTime currentDate = localDateTimeHolder.getCurrentDate();
        ProductUserNotification productUserNotification = ProductUserNotification.create(productId, userId,
                ActiveStatus.ACTIVATE, currentDate, currentDate);

        productUserNotificationRepository.save(productUserNotification);
    }

    public List<ProductUserNotification> getActiveProductNotifiers(Long productId) {
        return productUserNotificationRepository.findAllProductNotiActive(productId);
    }
}
