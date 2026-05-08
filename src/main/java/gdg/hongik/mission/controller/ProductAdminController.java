package gdg.hongik.mission.controller;

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
    public Product registerProduct(@RequestBody Product request){
        return productAdminService.registerProduct(request);

    }

    @PatchMapping("/{id}")
    public Map<String,Object> addstock (
            @PathVariable Long id,
            @RequestBody Map<String,Long> request) {

        Long addQuantity = request.get("addQuantity");
        return productAdminService.addstock(id, addQuantity);
    }

    @DeleteMapping
    public Map<String,Object> deleteProducts(@RequestBody Map<String, List<Long>> request ){
        // JSON에서 삭제할 ID 리스트 꺼내기
        List<Long> idsToRemove = request.get("productIds");
        return productAdminService.deleteProducts(idsToRemove);
    }


}
