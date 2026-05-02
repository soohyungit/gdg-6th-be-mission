package gdg.hongik.mission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    // 상품 ID
    private Long id;
    // 상품 이름
    private String name;
    // 상품 재고 수량
    private Long stockQuantity;
    // 상품 가격
    private Long price;
}
