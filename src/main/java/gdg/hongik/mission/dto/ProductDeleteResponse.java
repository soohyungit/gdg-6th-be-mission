package gdg.hongik.mission.dto;

import java.util.List;

public record ProductDeleteResponse(List<RemainingProduct> remainingProducts) {
    public record RemainingProduct(String name, Long stockQuantity){}
}
