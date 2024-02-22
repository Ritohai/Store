package ra.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ra.exception.customer.CustomerException;
import ra.exception.customer.EmptyException;
import ra.exception.customer.LoginException;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.OrderResponse;
import ra.model.entity.*;
import ra.repository.CartRepository;
import ra.repository.OrderDetailRepository;
import ra.repository.OrdersRepository;
import ra.security.userPrincipal.UserPrincipal;
import ra.service.IOrdersService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersServiceImpl implements IOrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    public List<OrderResponse> showToOrders(Authentication authentication, OrderRequest orderRequest) throws LoginException, EmptyException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new EmptyException("Yêu cầu đăng nhập.");
        }
        User user = userPrincipal.getUser();
        List<Orders> orderList = ordersRepository.findAllByUser(user);
        List<OrderResponse> orderResponses = new ArrayList<>();
        if (orderList.isEmpty()) {
            throw new EmptyException("Đơn hàng trống.");
        } else {
            for (Orders od : orderList) {
                OrderResponse o = OrderResponse.builder()
                        .userName(user.getUserName())
                        .ordersId(od.getOrdersId())
                        .address(od.getAddress())
                        .phone(od.getPhone())
                        .description(od.getDescription())
                        .createdDate(od.getCreatedDate())
                        .buyQuantity(quantity(od))
                        .totalPrice(price(od))
                        .status(od.getStatus())
                        .build();
                orderResponses.add(o);
            }
        }
        return orderResponses;
    }


    public Double price(Orders o) {
        double totailPrice = 0;
        for (OrderDetail od : o.getOrderDetails()) {
            totailPrice += (od.getQuantity() * od.getPrice());
        }
        return totailPrice;
    }

    private String name(Orders o) {
        StringBuilder nameProduct = new StringBuilder();
        for (OrderDetail od : o.getOrderDetails()) {
            nameProduct.append(od.getProduct().getNameProduct());
        }
        return nameProduct.toString();
    }

    public Integer quantity(Orders o) {
        int totailQUantity = 0;
        for (OrderDetail od : o.getOrderDetails()) {
            totailQUantity += od.getQuantity();
        }
        return totailQUantity;
    }


    @Override
    public String addToOrders(Authentication authentication, OrderRequest orderRequest) throws EmptyException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new EmptyException("Yêu cầu đăng nhập.");
        }
        User user = userPrincipal.getUser();
        List<Cart> carts = cartRepository.findAllByUser(user);
        if (carts.isEmpty()) {
            throw new EmptyException("Giỏ hàng trống.");
        }
        Orders orders = Orders.builder()
                .user(user)
                .ordersId(orderRequest.getOrdersId())
                .address(orderRequest.getAddress())
                .phone(orderRequest.getPhone())
                .description(orderRequest.getDescription())
                .createdDate(new Date())
                .status(StatusOrder.WAITING.toString())
                .build();
        ordersRepository.save(orders);
        for (Cart c : carts) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .orders(orders)
                    .product(c.getProduct())
                    .quantity(c.getQuantity())
                    .price(c.getProduct().getPrice())
                    .totalPrice(c.getProduct().getPrice() * c.getQuantity())
                    .build();
            orderDetailRepository.save(orderDetail);
            cartRepository.delete(c);
        }
        return "Success";
//        return OrderResponse.builder()
//                .ordersId(orders.getOrdersId())
//                .phone(orders.getPhone())
//                .address(orders.getAddress())
//                .description(orders.getDescription())
//                .createdDate(orders.getCreatedDate())
//                .status(orders.getStatus())
//                .status(StatusOrder.WAITING.name())
//                .build();

    }

    @Override
    public List<OrderResponse> getAllOrders() throws CustomerException {
        List<Orders> orderList = ordersRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        if (orderList.isEmpty()) {
            throw new CustomerException("Đơn hàng trống.");
        } else {
            for (Orders od : orderList) {
                OrderResponse o = OrderResponse.builder()
                        .userName(od.getUser().getUserName())
                        .ordersId(od.getOrdersId())
                        .address(od.getAddress())
                        .phone(od.getPhone())
                        .description(od.getDescription())
                        .createdDate(od.getCreatedDate())
                        .buyQuantity(quantity(od))
                        .totalPrice(price(od))
                        .status(od.getStatus())
                        .build();
                orderResponses.add(o);

            }
        }
        return orderResponses;
    }

    @Override
    public String changOrders(Authentication authentication, Long id) throws CustomerException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new CustomerException("Yêu cầu đăng nhập.");
        }
        Optional<Orders> orders = ordersRepository.findById(id);
        if (orders.isPresent()) {
            if (userPrincipal.getUser().getId().equals(orders.get().getUser().getId()) && orders.get().getStatus().equals(StatusOrder.WAITING.toString())) {
                orders.get().setStatus(StatusOrder.CANCEL.toString());
            }
            ordersRepository.save(orders.get());
            return "Đã hủy đơn hàng.";
        }
        return "Không thấy Id";
    }

    @Override
    public String changStatus(Long id, String status) throws CustomerException {
        Optional<Orders> orders = ordersRepository.findById(id);
        if (orders.isPresent()) {
            if (orders.get().getStatus().equals("WAITING")) {
                if (status.equals(StatusOrder.WAITING.name())) {
                    orders.get().setStatus(StatusOrder.CONFIRM.name());
                    ordersRepository.save(orders.get());
                    return "Đã thay đổi trạng thái đơn hàng sang xác nhận đơn.";
                } else {
                    orders.get().setStatus(StatusOrder.CANCEL.name());
                    ordersRepository.save(orders.get());
                    return "Đã thay đổi trạng thái đơn hàng sang hủy đơn hàng.";
                }
            } else if (orders.get().getStatus().equals("CONFIRM")) {
                if (status.equals(StatusOrder.CONFIRM.name())) {
                    orders.get().setStatus(StatusOrder.DELIVERY.name());
                    ordersRepository.save(orders.get());
                    return "Đã thay đổi trạng thái đơn hàng sang đang giao.";
                }
            } else if (orders.get().getStatus().equals("DELIVERY")) {
                if (status.equals(StatusOrder.DELIVERY.name())) {
                    orders.get().setStatus(StatusOrder.SUCCESS.name());
                    ordersRepository.save(orders.get());
                    return "Đã thay đổi trạng thái đơn hàng sang giao thành công.";
                } else if (status.equals(StatusOrder.CANCEL.name())) {
                    orders.get().setStatus(StatusOrder.CANCEL.name());
                    ordersRepository.save(orders.get());
                    return "Đã thay đổi trạng thái đơn hàng sang giao thất bại.";
                }
            }
        }
        return "Không thấy id";
    }
}

