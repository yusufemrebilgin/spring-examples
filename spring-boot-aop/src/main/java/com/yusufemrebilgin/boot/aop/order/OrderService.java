package com.yusufemrebilgin.boot.aop.order;

import com.yusufemrebilgin.boot.aop.order.aspect.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OrderService {

    public Order placeOrder(Long productId, int quantity) {
        simulateLatency();
        return Order.create(productId, quantity);
    }

    @Retryable
    public void processPayment(Long orderId) {
        simulateLatency();
        simulateRandomFailure();
    }

    private void simulateLatency() {
        try {
            TimeUnit.MILLISECONDS.sleep(400);
        } catch (InterruptedException ex) {
            // ...
        }
    }

    private void simulateRandomFailure() {
        if (Math.random() > 0.4) {
            throw new IllegalStateException("Payment gateway error");
        }
    }

}
