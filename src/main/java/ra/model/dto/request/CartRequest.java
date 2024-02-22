package ra.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartRequest {
    @NotNull(message = "Không để trống.")
    private Long productId;
    @NotNull(message = "Không để trống.")
    private Integer quantity;
}
