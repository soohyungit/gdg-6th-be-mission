package gdg.hongik.mission.dto;

import java.util.List;

public record PurchaseRequest(List<ItemRequest> items) {

    public record ItemRequest(Long productId, Long quantity){}

}
