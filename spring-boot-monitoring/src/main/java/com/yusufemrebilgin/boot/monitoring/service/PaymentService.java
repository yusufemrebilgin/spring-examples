package com.yusufemrebilgin.boot.monitoring.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final Random random = new Random();

    private final MeterRegistry meterRegistry;
    private final ObservationRegistry observationRegistry;

    private Counter paymentAttemptCounter;
    private Counter paymentSuccessCounter;
    private Counter paymentFailureCounter;

    PaymentService(MeterRegistry meterRegistry, ObservationRegistry observationRegistry) {
        this.meterRegistry = meterRegistry;
        this.observationRegistry = observationRegistry;
    }

    @PostConstruct
    private void registerMetrics() {
        this.paymentAttemptCounter = Counter.builder("payment.attempts")
                .description("Total payment attempts")
                .register(meterRegistry);
        this.paymentSuccessCounter = Counter.builder("payment.success")
                .description("Successful payments")
                .register(meterRegistry);
        this.paymentFailureCounter = Counter.builder("payment.failures")
                .description("Failed payments")
                .register(meterRegistry);
    }

    public void processPayment(String orderId) {
        Observation.createNotStarted("payment.process", observationRegistry)
                .contextualName("processing-payment")
                .observe(() -> {
                    paymentAttemptCounter.increment();
                    try {
                        // -------------
                        simulateDelay();
                        // -------------
                        simulateFailure();
                        // -------------
                        logger.info("Payment processed successfully for order '{}'", orderId);
                        paymentSuccessCounter.increment();
                    } catch (Exception ex) {
                        logger.error("Payment processed failed for order '{}'", orderId);
                        paymentFailureCounter.increment();
                        throw ex;
                    }
                });
    }

    private void simulateDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(80, 120));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Payment operation interrupted", ex);
        }
    }

    private void simulateFailure() {
        if (random.nextDouble() < 0.5) {
            throw new RuntimeException("Payment operation failed");
        }
    }

}
