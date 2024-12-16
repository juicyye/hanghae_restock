package hanghae.restock.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.product.StockStatus;
import hanghae.restock.domain.productnotificationhistory.ProductNotificationHistory;
import hanghae.restock.domain.productnotificationhistory.RestockNotificationStatus;
import hanghae.restock.mock.FakeProductNotificationHistoryRepository;
import hanghae.restock.mock.FakeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService productService;
    private FakeProductRepository productRepository;
    private FakeProductNotificationHistoryRepository productNotificationHistoryRepository;

    @BeforeEach
    void setUp() {
        productNotificationHistoryRepository = new FakeProductNotificationHistoryRepository();
        productRepository = new FakeProductRepository();
        productService = new ProductService(productRepository, productNotificationHistoryRepository);
        productRepository.save(new Product(1L, 0L, StockStatus.IN_STOCK));
    }

    @Test
    @DisplayName("리스탁을 진행하면 기존 재입고 회차 +1을 한다")
    void handleRestock() throws Exception {
        // given
        Long productId = 1L;

        // when
        Product result = productService.handleRestock(productId);

        // then
        assertAll(() -> {
            assertThat(result.getRestockPhase()).isEqualTo(1L);
            assertThat(result.getStockStatus()).isEqualTo(StockStatus.IN_STOCK);
        });
    }

    @Test
    @DisplayName("상품알림 정보를 DB에 저장할 수 있다")
    void addNotificationHistory() throws Exception {
        // given
        ProductNotificationHistory productNotificationHistory = new ProductNotificationHistory(1L, 1L, 0L,
                RestockNotificationStatus.COMPLETED, 10L);

        // when
        productNotificationHistoryRepository.save(productNotificationHistory);
        ProductNotificationHistory result = productNotificationHistoryRepository.findById(1L);

        // then
        assertAll(() -> {
            assertThat(result.getProductId()).isEqualTo(1L);
            assertThat(result.getRestockPhase()).isEqualTo(0L);
            assertThat(result.getRestockNotificationStatus()).isEqualTo(RestockNotificationStatus.COMPLETED);
            assertThat(result.getLastNotificationUserId()).isEqualTo(10L);
        });
    }

}