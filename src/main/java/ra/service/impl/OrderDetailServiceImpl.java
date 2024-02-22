package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ra.exception.customer.CustomerException;
import ra.exception.customer.EmptyException;
import ra.model.dto.response.OrderDetailResponse;
import ra.model.entity.OrderDetail;
import ra.model.entity.Orders;
import ra.model.entity.User;
import ra.repository.OrderDetailRepository;
import ra.repository.OrdersRepository;
import ra.security.userPrincipal.UserPrincipal;
import ra.service.IOrderDetailService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public List<OrderDetailResponse> getAllOrderDetail(Long orderId) throws CustomerException {
        List<Orders> orders = ordersRepository.findAll();
        List<OrderDetail> orderDetail = orderDetailRepository.findAll();
        List<OrderDetailResponse> list = new ArrayList<>();
        if (orders.isEmpty()) {
            throw new CustomerException("Không có đơn hàng nào.");
        } else {
            for (OrderDetail od : orderDetail) {
                if (od.getOrders().getOrdersId().equals(orderId)) {
                    OrderDetailResponse o = OrderDetailResponse.builder()
                            .orderDetailId(od.getOrderDetailId())
                            .orders(od.getOrders().getUser().getUserName())
                            .nameProduct(od.getProduct().getNameProduct())
                            .price(od.getPrice())
                            .quantity(od.getQuantity())
                            .totalPrice(od.getTotalPrice())
                            .build();
                    list.add(o);
                }
            }
        }
        return list;
    }

    @Override
    public List<OrderDetailResponse> showOrderById(Long orderId, Authentication authentication) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        User user = userPrincipal.getUser();
        List<Orders> orders = ordersRepository.findAllByUser(user);
        List<OrderDetail> orderDetail = orderDetailRepository.findAll();
        List<OrderDetailResponse> list = new ArrayList<>();
        if (orders.isEmpty()) {
            throw new CustomerException("Không có đơn hàng nào.");
        } else {
            for (OrderDetail od : orderDetail) {
                if (od.getOrders().getOrdersId().equals(orderId)) {
                    OrderDetailResponse o = OrderDetailResponse.builder()
                            .orderDetailId(od.getOrderDetailId())
                            .orders(od.getOrders().getUser().getUserName())
                            .nameProduct(od.getProduct().getNameProduct())
                            .price(od.getPrice())
                            .quantity(od.getQuantity())
                            .totalPrice(od.getTotalPrice())
                            .build();
                    list.add(o);
                }
            }
        }
        return list;
    }
}
