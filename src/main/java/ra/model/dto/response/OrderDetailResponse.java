package ra.model.dto.response;

import lombok.*;
import ra.model.entity.Orders;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailResponse {
    private String orders;
    private Long orderDetailId;
    private String nameProduct;
    private Double price;
    private Double totalPrice;
    private Integer quantity;

}
