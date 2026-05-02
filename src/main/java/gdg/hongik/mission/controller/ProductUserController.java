package gdg.hongik.mission.controller;

import gdg.hongik.mission.Product;
import gdg.hongik.mission.ProductStore;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class ProductUserController {

    @GetMapping("/products")
    // 상품 조회하면 Product 클래스 필드의 모든 값을 반환하는것이므로 Product를 return type 으로
    public Product getProductByName(@RequestParam String name){
        // productStore(DB) 에서 이름이 일치하는 상품 찾기
        for(Product p : ProductStore.products){
            if(p.getName().equals(name)){
                return p;
            }
        }

        throw new RuntimeException("해당 이름의 상품이 존재하지 않습니다");
    }


    @PostMapping("/orders")
    public Map<String, Object> purchaseProducts(@RequestBody Map<String, List<Map<String, Object>>> request) {
        // 요청 데이터에서 구매할 아이템들 꺼내기 items : [배열] <- 이 리스트 가져오기
        List<Map<String, Object>> items = request.get("items");

        long totalPrice = 0;
        // 실제 구매할 아이템들 저장할 list
        List<Map<String, Object>> purchasedItems = new ArrayList<>();

        for (Map<String, Object> item : items) {
            // 만약 JSON의 숫자가 integer로 인식될때를 대비한 명시적 형변환

//            Long productId = (Long) item.get("productId");
//            Long quantity = (Long) item.get("quantity");
            Long productId = Long.valueOf(String.valueOf(item.get("productId")));
            Long quantity = Long.valueOf(String.valueOf(item.get("quantity")));

            Product targetProduct = null;
            for (Product p : ProductStore.products) {
                if (p.getId().equals(productId)) {
                    targetProduct = p;
                    break;
                }
            }
            // 상품 없거나 재고 부족할때
            if (targetProduct == null || targetProduct.getStockQuantity() < quantity) {
                throw new RuntimeException("상품이 없거나 재고가 부족합니다.");
            }

            // 재고에서 구매할 수량만큼 가져오고 총 가격 계산
            targetProduct.setStockQuantity(targetProduct.getStockQuantity() - quantity);
            long itemTotalPrice = targetProduct.getPrice() * quantity;
            totalPrice += itemTotalPrice;

            // 진짜로 구매하는 아이템들 리스트에 입력
            Map<String, Object> purchasedItem = new HashMap<>();
            purchasedItem.put("name", targetProduct.getName());
            purchasedItem.put("quantity", quantity);
            purchasedItem.put("itemTotalPrice", itemTotalPrice);
            purchasedItems.add(purchasedItem);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("totalPrice", totalPrice);
        response.put("purchasedItems", purchasedItems);

        return response;
    }
}
