package hanghae.restock.controller;

import hanghae.restock.service.RestockFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final RestockFacade restockFacade;

    @PostMapping("/products/{productId}/notifications/re-stock")
    public ResponseEntity<?> reStock(@PathVariable("productId") Long productId) {
        restockFacade.handleRestockProcess(productId);
        return ResponseEntity.ok().build();
    }
}
