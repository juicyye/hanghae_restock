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

    /**
     * 상품 재입고 알림을 신청한다
     */
    public void addNotification(Long productId, Long userId) {
        LocalDateTime currentDate = localDateTimeHolder.getCurrentDate();
        ProductUserNotification productUserNotification = ProductUserNotification.create(productId, userId,
                ActiveStatus.ACTIVATE, currentDate, currentDate);

        productUserNotificationRepository.save(productUserNotification);
    }

    /**
     * 상품 재입고 알림이 활성화된 유저를 가져온다
     */
    public List<ProductUserNotification> getActiveProductNotifiers(Long productId, long cursor, int size) {
        return productUserNotificationRepository.findAllProductNotification(productId, cursor, size, ActiveStatus.ACTIVATE);
    }
}
