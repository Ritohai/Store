package ra.model.dto.response;

import lombok.*;
import ra.model.entity.Orders;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrdersAdminResponse {
    private Orders orders;
    private String statusOrders;
}
