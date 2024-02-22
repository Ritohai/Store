package ra.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ra.exception.customer.CustomerException;
import ra.exception.customer.EmptyException;
import ra.exception.customer.LoginException;
import ra.model.dto.request.OrderRequest;

import ra.model.dto.response.OrderResponse;
import ra.model.entity.Orders;
import ra.service.IOrderDetailService;
import ra.service.IOrdersService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/orders")
public class OrderController {
    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private IOrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> showToOrders(@RequestBody OrderRequest orderRequest, Authentication authentication) throws LoginException, EmptyException {
        return new ResponseEntity<>(ordersService.showToOrders(authentication, orderRequest), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> showOrderByOrderId(@PathVariable Long orderId, Authentication authentication) throws CustomerException {
        return new ResponseEntity<>(orderDetailService.showOrderById(orderId, authentication), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> createToOrders(@Valid @RequestBody OrderRequest orderRequest, Authentication authentication) throws EmptyException {
        return new ResponseEntity<>(ordersService.addToOrders(authentication, orderRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<String> changOrders(@PathVariable Long id, Authentication authentication) throws CustomerException {
        return new ResponseEntity<>(ordersService.changOrders(authentication, id), HttpStatus.OK);
    }



}