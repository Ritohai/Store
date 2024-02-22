package ra.service;

import org.springframework.security.core.Authentication;
import ra.exception.customer.CustomerException;
import ra.exception.customer.EmptyException;
import ra.exception.customer.LoginException;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.OrderResponse;


import java.util.List;

public interface IOrdersService {

    List<OrderResponse> showToOrders(Authentication authentication, OrderRequest orderRequest) throws LoginException, EmptyException;

    String addToOrders(Authentication authentication, OrderRequest orderRequest) throws EmptyException;

    List<OrderResponse> getAllOrders() throws CustomerException;

    String changOrders(Authentication authentication, Long id) throws CustomerException;

    String changStatus(Long id, String status) throws CustomerException;
}
