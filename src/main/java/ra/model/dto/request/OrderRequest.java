package ra.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import ra.model.entity.User;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequest {
    @NotNull
    @Pattern(regexp = "^0\\d{9,10}$", message = "Số điện thoại không hợp lệ.")
    private String phone;
    @NotEmpty(message = "Không để trống.")
    private String address;
    @NotEmpty(message = "Không để trống.")
    private String description;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;
    private Long ordersId;
    private Integer buyQuantity;
    private Double totalPrice;
    private String status;

}
