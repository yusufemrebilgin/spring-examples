package com.yusufemrebilgin.boot.aop.order;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order placeOrder(@RequestParam Long orderId, @RequestParam int quantity) {
        return orderService.placeOrder(orderId, quantity);
    }

    @PostMapping("/{orderId}/payment")
    public void processPayment(@PathVariable Long orderId) {
        orderService.processPayment(orderId);
    }

}
