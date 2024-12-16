package hanghae.restock.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.product.StockStatus;
import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;
import hanghae.restock.domain.productnotificationhistory.RestockNotificationStatus;
import hanghae.restock.domain.productusernotification.ActiveStatus;
import hanghae.restock.domain.productusernotification.ProductUserNotification;
import hanghae.restock.mock.FakeLocalDateTimeHolder;
import hanghae.restock.mock.FakeProductNotificationHistoryRepository;
import hanghae.restock.mock.FakeProductRepository;
import hanghae.restock.mock.FakeProductUserNotificationHistoryRepository;
import hanghae.restock.mock.FakeProductUserNotificationRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RestockFacadeTest {

    private RestockFacade restockFacade;
    private FakeProductRepository productRepository;
    private FakeProductNotificationHistoryRepository productNotificationHistoryRepository;
    private FakeProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;
    private LocalDateTime localDateTime = LocalDateTime.now();
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productUserNotificationHistoryRepository = new FakeProductUserNotificationHistoryRepository();
        FakeProductUserNotificationRepository productUserNotificationRepository = new FakeProductUserNotificationRepository();
        productNotificationHistoryRepository = new FakeProductNotificationHistoryRepository();
        productRepository = new FakeProductRepository();
        productService = new ProductService(productRepository, productNotificationHistoryRepository);
        FakeLocalDateTimeHolder localDateTimeHolder = new FakeLocalDateTimeHolder(localDateTime);
        UserService userService = new UserService(productUserNotificationRepository, localDateTimeHolder);
        UserNotificationService userNotificationService = new UserNotificationService(
                userService, localDateTimeHolder, productUserNotificationHistoryRepository);
        restockFacade = new RestockFacade(productService,userNotificationService);

        productRepository.save(createProduct(1L, 0L, StockStatus.IN_STOCK));
        productUserNotificationRepository.save(createUserNotification(1L, 1L, localDateTime));
        productUserNotificationRepository.save(createUserNotification(1L, 2L, localDateTime));
    }

    @Test
    @DisplayName("리스탁을 진행하면 상품 알림을 저장하고 유저들에게 알림을 보낼 수 있다")
    void executeUserNotification() throws Exception {
        // given
        Long productId = 1L;

        // when
        restockFacade.handleRestockProcess(productId);
        ProductNotificationHistory result = productNotificationHistoryRepository.findById(0L);

        // then
        assertAll(() -> {
            assertThat(result.getLastNotificationUserId()).isEqualTo(2L);
            assertThat(result.getRestockPhase()).isEqualTo(1L);
            assertThat(result.getRestockNotificationStatus()).isEqualByComparingTo(RestockNotificationStatus.COMPLETED);
        });
    }

    @Test
    @DisplayName("재고가 부족하면 알림 정보에는 재고부족으로 저장된다")
    void OutOfStockError() throws Exception {
        // given
        Long productId = 1L;
        productService.changeProductStockStatus(productId, StockStatus.OUT_OF_STOCK);

        // when
        restockFacade.handleRestockProcess(productId);
        ProductNotificationHistory result = productNotificationHistoryRepository.findById(0L);

        // then
        assertAll(() -> {
            assertThat(result.getRestockPhase()).isEqualTo(1L);
            assertThat(result.getRestockNotificationStatus()).isEqualByComparingTo(RestockNotificationStatus.CANCELED_BY_SOLD_OUT);
        });
    }

    private Product createProduct(Long id, long restockPhase, StockStatus stockStatus) {
        return new Product(id, restockPhase, stockStatus);
    }

    private ProductUserNotification createUserNotification(long productId, long userId, LocalDateTime currentDate) {
        return new ProductUserNotification(null, productId, userId, ActiveStatus.ACTIVATE,
                currentDate, currentDate);
    }

}