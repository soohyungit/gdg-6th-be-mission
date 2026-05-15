package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.service.ProductAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    // 상품 등록
    @PostMapping
    public Product createProduct(@RequestBody ProductCreateRequest request){
        return productAdminService.createProduct(request);
    }

    @PatchMapping("/{id}")
    public ProductAddResponse addStock (
            @PathVariable Long id,
            @RequestBody ProductAddRequest request) {

        Long addQuantity = request.addQuantity();
        return productAdminService.addStock(id, addQuantity);
    }

    @DeleteMapping
    public ProductDeleteResponse deleteProducts(@RequestBody ProductDeleteRequest request ){
        return productAdminService.deleteProducts(request);
    }


}
