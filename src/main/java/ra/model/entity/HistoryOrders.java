package ra.model.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HistoryOrders {
    private Long historyId;
    private Orders ordersId;
    private String user;
    private String address;
    private String phone;
    private String description;
    private String nameProduct;
    private Integer buyQuantity;
    private Double totalPrice;
    private String statusOrders;
}
