package com.yusufemrebilgin.boot.aop.guide;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class NotVeryUsefulAspect {

    // The method serving as the pointcut signature must have a void return type.
    //
    // The following example defines a pointcut named anyOldTransfer that matches
    // the execution of any method named transfer:

    @Pointcut("execution(* transfer(..))")      // pointcut expression
    private void anyOldTransfer() {}            // pointcut signature

    // Supported pointcut designators:
    // https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html#aop-pointcuts-designators



}
