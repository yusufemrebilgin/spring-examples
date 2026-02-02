package com.yusufemrebilgin.boot.monitoring.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.annotation.Observed;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToDoubleFunction;

@Service
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    private final Random random = new Random();
    private final Map<Integer, AtomicInteger> inventory = new ConcurrentHashMap<>();

    private final MeterRegistry meterRegistry;

    private Counter inventoryCheckCounter;

    InventoryService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        loadSampleProductDataToInventory(random.nextInt(10, 30));
    }

    @PostConstruct
    private void registerMetrics() {
        this.inventoryCheckCounter = Counter.builder("inventory.checks")
                .description("Number of inventory checks performed")
                .register(meterRegistry);
        // Register gauges for inventory levels for demo purposes
        ToDoubleFunction<Map<Integer, AtomicInteger>> sumInventoryFunction = map -> map.values().stream()
                .mapToInt(AtomicInteger::get)
                .sum();

        Gauge.builder("inventory.stock.total", inventory, sumInventoryFunction)
                .description("Total inventory stock across all products")
                .register(meterRegistry);
    }

    @Observed(name = "inventory.check", contextualName = "checking-inventory")
    public boolean hasStock(Integer productId, int quantity) {
        logger.info("Checking stock [productId={}, quantity={}]", productId, quantity);
        inventoryCheckCounter.increment();
        simulateDelay();

        AtomicInteger stock = inventory.get(productId);
        if (stock == null) {
            return false;
        }

        return quantity <= stock.get();
    }

    private void simulateDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(20, 100));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Inventory operation interrupted", ex);
        }
    }

    private void loadSampleProductDataToInventory(int size) {
        for (int i = 1; i <= size; i++) {
            AtomicInteger productStock = new AtomicInteger(random.nextInt(100));
            inventory.put(i, productStock);
        }
    }

}
