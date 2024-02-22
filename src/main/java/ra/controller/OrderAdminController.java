package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ra.exception.customer.CustomerException;
import ra.model.dto.response.OrderDetailResponse;
import ra.model.dto.response.OrderResponse;
import ra.model.entity.OrderDetail;
import ra.service.IOrderDetailService;
import ra.service.IOrdersService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin/orders")
public class OrderAdminController {
    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private IOrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrder() throws CustomerException {
        return new ResponseEntity<>(ordersService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderDetailResponse>> getAllOrderDetail(@PathVariable Long orderId) throws CustomerException{
        return new ResponseEntity<>(orderDetailService.getAllOrderDetail(orderId), HttpStatus.OK);
    }

    @PatchMapping("/id={id}&&status={status}")
    public ResponseEntity<String> changStatusOrderUser(@PathVariable Long id,@PathVariable String status) throws CustomerException {
        return new ResponseEntity<>(ordersService.changStatus(id, status), HttpStatus.OK);
    }

}
