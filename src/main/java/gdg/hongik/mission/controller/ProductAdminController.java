package gdg.hongik.mission.controller;

import gdg.hongik.mission.Product;
import gdg.hongik.mission.ProductStore;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductAdminController {

    // 상품 등록
    @PostMapping
    public Product registerProduct(@RequestBody Product request){
        for(Product p : ProductStore.products){
            if(p.getName().equals(request.getName())){
                throw new RuntimeException("이미 존재하는 상품 이름입니다.");
            }
        }

        Product newProduct = new Product();
        newProduct.setId(ProductStore.sequence++);
        newProduct.setName(request.getName());
        newProduct.setPrice(request.getPrice());
        newProduct.setStockQuantity(request.getStockQuantity());

        ProductStore.products.add(newProduct);

        return newProduct;

    }

    @PatchMapping("/{id}")
    public Map<String,Object> addstock (
            @PathVariable Long id,
            @RequestBody Map<String,Long> request) {

        Long addQuantity = request.get("addQuantity");

        Product targetProduct = null;
        for(Product p : ProductStore.products){
            if(p.getId().equals(id)){
                targetProduct = p;
                break;
            }
        }

        if(targetProduct == null){
            throw new RuntimeException("해당 ID의 상품을 찾을 수 없습니다");
        }

        targetProduct.setStockQuantity(targetProduct.getStockQuantity() + addQuantity);


        Map<String, Object> response = new HashMap<>();
        response.put("name", targetProduct.getName());
        response.put("stockQuantity", targetProduct.getStockQuantity());

        return response;
    }

    @DeleteMapping
    public Map<String,Object> deleteProducts(@RequestBody Map<String, List<Long>> request ){
        // JSON에서 삭제할 ID 리스트 꺼내기
        List<Long> idsToRemove = request.get("productIds");

        // productStore DB에서 해당 ID 삭제
        // 이때 removeIf는 조건에 맞는 데이터를 한번에 지워주는 편리한 메서드
        ProductStore.products.removeIf(product -> idsToRemove.contains(product.getId()));

        // 삭제후 남은 상품들 목록 만들기
        List<Map<String, Object>> remainingList =new ArrayList<>();

        for(Product p : ProductStore.products){
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("name", p.getName());
            productMap.put("stockQuantity", p.getStockQuantity());
            remainingList.add(productMap);
        }

        // 최종 응답 구조
        Map<String,Object> response = new HashMap<>();
        response.put("remainingProducts", remainingList);

        return response;
    }


}
