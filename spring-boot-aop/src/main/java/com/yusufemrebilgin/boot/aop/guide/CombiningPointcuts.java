package com.yusufemrebilgin.boot.aop.guide;

import org.aspectj.lang.annotation.Pointcut;

public class CombiningPointcuts {

    //
    // Matches if a method execution join point represents the execution of any public method.
    //

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {}

    //
    // Matches if a method execution is in the trading module.
    //

    @Pointcut("within(com.xyz.trading..*)")
    public void inTrading() {}

    //
    // Matches if a method execution represents any public method in the trading module.
    //

    @Pointcut("publicMethod() && inTrading()")
    public void tradingOperation() {}

    // It is a best practice to build more complex pointcut expressions out of smaller named pointcuts, as shown above.
    // When referring to pointcuts by name, normal Java visibility rules apply (you can see private pointcuts in the
    // same type, protected pointcuts in the hierarchy, public pointcuts anywhere, and so on). Visibility does not
    // affect pointcut matching.
    //
    // https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html#aop-pointcuts-combining

}
