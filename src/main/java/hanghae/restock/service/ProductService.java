package hanghae.restock.service;

import hanghae.restock.domain.product.Product;
import hanghae.restock.domain.product.StockStatus;
import hanghae.restock.service.port.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @PostConstruct
    public void create(){
        productRepository.save(new Product(null, 0L, StockStatus.IN_STOCK));
    }

}
