package hanghae.restock.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import hanghae.restock.domain.productusernotification.ProductUserNotification;
import hanghae.restock.mock.FakeLocalDateTimeHolder;
import hanghae.restock.mock.FakeProductUseNotificationRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserService userService;
    private FakeLocalDateTimeHolder localDateTimeHolder;

    @BeforeEach
    void setUp() {
        FakeProductUseNotificationRepository repository = new FakeProductUseNotificationRepository();
        localDateTimeHolder = new FakeLocalDateTimeHolder(LocalDateTime.now());
        userService = new UserService(repository, localDateTimeHolder);
    }

    @Test
    @DisplayName("상품 재입고 알림을 설정한 유저를 저장하고 조회할 수 있다")
    void addNotification() throws Exception {
        // given
        Long productId = 1L;
        Long userId = 1L;

        // when
        userService.addNotification(productId, userId);
        List<ProductUserNotification> results = userService.getActiveProductNotifiers(productId);

        // then
        assertThat(results).hasSize(1)
                .extracting(ProductUserNotification::getUserId)
                .contains(userId);
    }

}