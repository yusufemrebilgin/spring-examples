package com.yusufemrebilgin.boot.aop.order.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    @Pointcut("execution(* com.yusufemrebilgin.boot.aop.order.OrderService.processPayment(..))")
    private void processPayment() {}

    @Pointcut("args(productId, ..)")
    private void productIdArg(Long productId) {}

    @AfterThrowing(
            pointcut = "processPayment() && productIdArg(productId)",
            throwing = "ex",
            argNames = "productId, ex"
    )
    public void afterFailure(Long productId, Exception ex) {
        logger.error("Failure for product {}: {}", productId, ex.getMessage());
    }

}
