package com.yusufemrebilgin.boot.monitoring.controller;

import com.yusufemrebilgin.boot.monitoring.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> placeOrder(@RequestParam Integer productId, @RequestParam Integer quantity) {
        orderService.placeOrder(productId, quantity);
        return ResponseEntity.noContent().build();
    }

}
