package ra.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailRequest {
    private String orders;
    private String nameProduct;
    private Double price;
    private Double totalPrice;
    private Integer quantity;
}
