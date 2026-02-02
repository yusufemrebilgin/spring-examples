package com.yusufemrebilgin.boot.monitoring.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.annotation.Observed;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final MeterRegistry meterRegistry;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    private Counter orderSuccessCounter;
    private Counter orderFailureCounter;

    OrderService(MeterRegistry meterRegistry, PaymentService paymentService, InventoryService inventoryService) {
        this.meterRegistry = meterRegistry;
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }

    @PostConstruct
    private void registerMetrics() {
        this.orderSuccessCounter = Counter.builder("order.success")
                .description("Successfully completed orders")
                .register(meterRegistry);
        this.orderFailureCounter = Counter.builder("order.failure")
                .description("Failed orders")
                .register(meterRegistry);
    }

    @Observed(name = "order.place", contextualName = "placing-order")
    public void placeOrder(Integer productId, int quantity) {
        String orderId = UUID.randomUUID().toString();
        logger.info("Placing order for id '{}' [productId={}, quantity={}]", orderId, productId, quantity);

        if (!inventoryService.hasStock(productId, quantity)) {
            logger.warn("Order '{}' failed due to insufficient stock", orderId);
            orderFailureCounter.increment();
            throw new RuntimeException("Failed due to insufficient stock");
        }

        try {
            paymentService.processPayment(orderId);
            orderSuccessCounter.increment();
            logger.info("Order '{}' completed successfully", orderId);
        } catch (Exception ex) {
            orderFailureCounter.increment();
            logger.error("Order '{}' failed during payment", orderId);
            throw new RuntimeException(ex);
        }
    }

}
