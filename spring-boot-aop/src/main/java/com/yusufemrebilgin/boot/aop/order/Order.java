package com.yusufemrebilgin.boot.aop.order;

public record Order(Long id, Long productId, int quantity) {

    private static long dummyIdSequence = 1;

    public static Order create(Long productId, int quantity) {
        return new Order(dummyIdSequence++, productId, quantity);
    }

}
