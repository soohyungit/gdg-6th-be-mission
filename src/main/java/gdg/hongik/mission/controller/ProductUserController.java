package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.PurchaseRequest;
import gdg.hongik.mission.dto.PurchaseResponse;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.service.ProductUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor

public class ProductUserController {

    private final ProductUserService productUserService;

    @GetMapping("/products")
    // 상품 조회하면 Product 클래스 필드의 모든 값을 반환하는것이므로 Product를 return type 으로
    public Product getProductByName(@RequestParam String name){
        return productUserService.getProductByName(name);
    }


    @PostMapping("/orders")
    public PurchaseResponse purchaseProducts(@RequestBody PurchaseRequest request) {
        return productUserService.purchaseProducts(request);
    }
}
