package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.exception.customer.CustomerException;
import ra.service.IProductService;

@RestController
@RequestMapping("/api/v1/user/product")
public class ProductUserController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProduct(Authentication authentication) throws CustomerException {
        return new ResponseEntity(productService.getAllProductByUser(authentication), HttpStatus.OK);
    }
}
