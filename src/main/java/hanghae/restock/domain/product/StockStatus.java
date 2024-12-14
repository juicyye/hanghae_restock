package hanghae.restock.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StockStatus {

    IN_STOCK("재고 있음"), OUT_OF_STOCK("품절");
    private final String description;
}
