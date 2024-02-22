package ra.model.dto.request;

import lombok.*;
import ra.model.entity.Orders;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrdersAdminRequest {
    private Orders orders;

}
