package ra.model.dto.request;

import lombok.*;
import ra.model.entity.OrderDetail;
import ra.model.entity.Orders;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HistoryRequest {
    private Long ordersId;
    private String user;
    private String address;
    private String phone;
    private String description;
    private String nameProduct;
    private Integer buyQuantity;
    private Double totalPrice;
}
