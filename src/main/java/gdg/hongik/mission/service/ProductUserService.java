package gdg.hongik.mission.service;

import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class ProductUserService {
    private final ProductRepository productRepository;

    // user의 비즈니스 로직
    // 1. 상품조회 2. 상품 구매
    public Product getProductByName(String name){
        Product product = productRepository.findByName(name);
        if(product == null){
            throw new RuntimeException("해당 이름의 상품이 존재하지 않습니다.");
        }
        return product;
    }

    public Map<String,Object> purchaseProducts(List<Map<String, Object>> items){
        // 구매할 아이템 리스트(id, 수량)를 매개변수로 받기

        long totalPrice = 0;
        // 구매한 아이템들의 정보를 담을 리스트
        List<Map<String, Object>> purchasedItems = new ArrayList<>();

        for (Map<String, Object> item : items) {

            // 만약 JSON의 숫자가 integer로 인식될때를 대비한 명시적 형변환
            Long productId = Long.valueOf(String.valueOf(item.get("productId")));
            Long quantity = Long.valueOf(String.valueOf(item.get("quantity")));

            Product targetProduct = productRepository.findById(productId);

            // 상품 없거나 재고 부족할때
            if (targetProduct == null || targetProduct.getStockQuantity() < quantity) {
                throw new RuntimeException("상품이 없거나 재고가 부족합니다.");
            }

            // 재고에서 구매할 수량만큼 가져오고 총 가격 계산
            targetProduct.setStockQuantity(targetProduct.getStockQuantity() - quantity);
            long itemTotalPrice = targetProduct.getPrice() * quantity;
            totalPrice += itemTotalPrice;

            // 구매하는 아이템들의 정보(이름, 수량, 가격) 담기
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
