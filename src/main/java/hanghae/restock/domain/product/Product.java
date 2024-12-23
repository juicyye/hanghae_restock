package hanghae.restock.domain.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    private Long id;
    private Long restockPhase;
    private StockStatus stockStatus;

    public void addRestockPhase(){
        this.restockPhase++;
    }

    public boolean availableStockNotification(){
        return stockStatus.equals(StockStatus.IN_STOCK);
    }

    public void updateStockStatus(StockStatus stockStatus){
        this.stockStatus = stockStatus;
    }


}
