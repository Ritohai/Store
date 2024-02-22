package ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "ordersId",nullable = false)
    private Orders orders;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId",nullable = false)
    private Product product;
    private Double price;
    private Double totalPrice;
    private Integer quantity;

}
