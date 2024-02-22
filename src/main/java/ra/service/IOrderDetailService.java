package ra.service;

import org.springframework.security.core.Authentication;
import ra.exception.customer.CustomerException;
import ra.model.dto.response.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetailResponse> getAllOrderDetail(Long orderId) throws CustomerException;
    List<OrderDetailResponse> showOrderById(Long orderId, Authentication authentication) throws CustomerException;

}
