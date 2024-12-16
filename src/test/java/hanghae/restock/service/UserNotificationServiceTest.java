package hanghae.restock.service;

import static org.assertj.core.api.Assertions.assertThat;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.product.StockStatus;
import hanghae.restock.domain.productusernotification.ActiveStatus;
import hanghae.restock.domain.productusernotification.ProductUserNotification;
import hanghae.restock.domain.productusernotificationhistory.ProductUserNotificationHistory;
import hanghae.restock.mock.FakeLocalDateTimeHolder;
import hanghae.restock.mock.FakeProductUserNotificationHistoryRepository;
import hanghae.restock.mock.FakeProductUserNotificationRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserNotificationServiceTest {

    private UserNotificationService userNotificationService;
    private FakeLocalDateTimeHolder localDateTimeHolder = new FakeLocalDateTimeHolder(LocalDateTime.now());
    private FakeProductUserNotificationHistoryRepository userHistoryRepository;

    @BeforeEach
    void setUp() {
        FakeProductUserNotificationRepository productUserNotificationRepository = new FakeProductUserNotificationRepository();
        userHistoryRepository = new FakeProductUserNotificationHistoryRepository();
        UserService userService = new UserService(productUserNotificationRepository, localDateTimeHolder);
        userNotificationService = new UserNotificationService(userService,localDateTimeHolder, userHistoryRepository);

        LocalDateTime currentDate = localDateTimeHolder.getCurrentDate();
        productUserNotificationRepository.save(createUserNotification(1L, 1L, currentDate.minusDays(1)));
        productUserNotificationRepository.save(createUserNotification(1L, 2L, localDateTimeHolder.getCurrentDate()));
    }

    @Test
    @DisplayName("상품에 재고가 있으면 알림 신청한 유저들을 저장한다")
    void loadRestockNotificationUsers() throws Exception {
        // given
        Product product = new Product(1L, 0L, StockStatus.IN_STOCK);

        // when
        userNotificationService.processNotification(product, 0L);
        Long result = userNotificationService.getLastNotificationUserId(0L);

        // then
        assertThat(result).isEqualTo(2L);
    }

    @Test
    @DisplayName("캐시에 저장한 유저 알림 정보들을 DB에 저장할 수 있다")
    void saveRestockHistory() throws Exception {
        // given
        Product product = new Product(1L, 0L, StockStatus.IN_STOCK);
        userNotificationService.processNotification(product, 0L);

        // when
        List<ProductUserNotificationHistory> results = userHistoryRepository.getData();

        // then
        assertThat(results).hasSize(2)
                .extracting(ProductUserNotificationHistory::getUserId)
                .containsExactly(1L, 2L);
    }

    public ProductUserNotification createUserNotification(long productId, long userId, LocalDateTime currentDate) {
        return new ProductUserNotification(null, productId, userId, ActiveStatus.ACTIVATE,
                currentDate, currentDate);
    }

}