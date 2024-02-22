package ra.model.dto.response;

import lombok.*;
import ra.model.entity.OrderDetail;
import ra.model.entity.Orders;
import ra.model.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HistoryResponse {
    private Long historyId;
    private Long ordersId;
    private String user;
    private String address;
    private String phone;
    private String description;
    private String nameProduct;
    private Integer buyQuantity;
    private Double totalPrice;
    private String statusOrders;
}
