package gdg.hongik.mission.dto;

import java.util.List;

public record PurchaseResponse(Long totalPrice, List<PurchasedItemResponse> purchasedItems) {

    public record PurchasedItemResponse(String name, Long quantity, long itemTotalPrice ){}
}
