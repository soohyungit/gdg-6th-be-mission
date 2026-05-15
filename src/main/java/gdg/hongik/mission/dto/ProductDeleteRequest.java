package gdg.hongik.mission.dto;

import java.util.List;

public record ProductDeleteRequest(List<Long> productIds) {
}
