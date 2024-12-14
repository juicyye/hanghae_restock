package hanghae.restock.service;

import static org.assertj.core.api.Assertions.assertThat;

import hanghae.restock.domain.productusernotification.ActiveStatus;
import hanghae.restock.domain.productusernotification.ProductUserNotification;
import hanghae.restock.mock.FakeLocalDateTimeHolder;
import hanghae.restock.mock.FakeProductUseNotificationRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserNotificationServiceTest {

    private UserNotificationService userNotificationService;
    private FakeLocalDateTimeHolder localDateTimeHolder = new FakeLocalDateTimeHolder(LocalDateTime.now());

    @BeforeEach
    void setUp() {
        FakeProductUseNotificationRepository productUseNotificationRepository = new FakeProductUseNotificationRepository();
        UserService userService = new UserService(productUseNotificationRepository, localDateTimeHolder);
        userNotificationService = new UserNotificationService(userService);

        LocalDateTime currentDate = localDateTimeHolder.getCurrentDate();
        productUseNotificationRepository.save(createUserNotification(1L, 1L, currentDate.minusDays(1)));
        productUseNotificationRepository.save(createUserNotification(1L, 2L, localDateTimeHolder.getCurrentDate()));
    }

    @Test
    @DisplayName("상품 재입고 알림을 신청한 유저들을 저장할 수 있다")
    void processNotification() throws Exception {
        // given
        Long productId = 1L;
        Long restockPhase = 0L;

        // when
        userNotificationService.processNotification(productId, restockPhase);
        Long result = userNotificationService.getLastNotificationUserId(restockPhase);

        // then
        assertThat(result).isEqualTo(2L);
    }

    public ProductUserNotification createUserNotification(long productId, long userId, LocalDateTime currentDate) {
        return new ProductUserNotification(null, productId, userId, ActiveStatus.ACTIVATE,
                currentDate, currentDate);
    }

}