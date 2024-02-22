package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private User user;
    private String phone;
    private String address;
    private String description;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    private String status;
    @OneToMany(mappedBy = "orders")
    private Set<OrderDetail> orderDetails;

}
