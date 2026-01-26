package com.yusufemrebilgin.boot.aop.order.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {

    private static final Logger logger = LoggerFactory.getLogger(RetryAspect.class);

    @Around("@annotation(retryable)")
    public Object retry(ProceedingJoinPoint proceedingJoinPoint, Retryable retryable) throws Throwable {
        int attempts = retryable.times();
        Throwable lastException = null;
        for (int i = 1; i <= attempts; i++) {
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable ex) {
                lastException = ex;
                logger.warn("Attempt {}/{} failed: {}", i, attempts, ex.getMessage());
            }
        }

        throw lastException;
    }

}
