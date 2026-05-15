package gdg.hongik.mission.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    //상품 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품 이름
    private String name;

    private Long stockQuantity;

    private Long price;

    public Product(String name, Long stockQuantity, Long price){
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }
}
