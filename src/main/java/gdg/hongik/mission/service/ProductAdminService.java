package gdg.hongik.mission.service;

import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductAdminService {
    private final ProductRepository productRepository;

    public Product registerProduct(Product request){
        Product exsitingProduct = productRepository.findByName(request.getName());
        if(exsitingProduct != null){
            throw new RuntimeException("동일한 이름의 상품이 존재합니다.");
        }

        //product 클래스는 기본생성자만 있으므로 setter로 product 생성
        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setId(request.getId());

        productRepository.save(product);
        return product;
    }

    public Map<String, Object> addstock(Long id, Long addQuantity){

        Product targetProduct = productRepository.findById(id);

        if(targetProduct == null){
            throw new RuntimeException("해당 ID의 상품을 찾을 수 없습니다");
        }

        targetProduct.setStockQuantity(targetProduct.getStockQuantity() + addQuantity);


        Map<String, Object> response = new HashMap<>();
        response.put("name", targetProduct.getName());
        response.put("stockQuantity", targetProduct.getStockQuantity());

        return response;

    }

    public Map<String, Object> deleteProducts(List<Long> request){
        // 리스트에 있는 id들 반복문으로 돌면서 아이템 삭제하기
        for(Long id : request){
            productRepository.deleteById(id);
        }
        // 삭제후 남은 상품들 목록 만들기
        List<Product> allProducts  = productRepository.findAll();

        List<Map<String, Object>> remainingList =new ArrayList<>();

        for(Product p : allProducts){
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
