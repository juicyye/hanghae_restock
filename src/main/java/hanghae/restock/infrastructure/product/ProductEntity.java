package hanghae.restock.infrastructure.product;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.product.StockStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long restockPhase;
    private StockStatus stockStatus;

    public static ProductEntity fromModel(Product domain) {
        return new ProductEntity(
                domain.getId(), domain.getRestockPhase(), domain.getStockStatus()
        );
    }

    public Product toModel(){
        return new Product(
                id, restockPhase, stockStatus
        );
    }
}
