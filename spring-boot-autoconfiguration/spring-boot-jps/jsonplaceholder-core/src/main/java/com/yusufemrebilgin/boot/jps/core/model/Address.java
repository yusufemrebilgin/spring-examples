package com.yusufemrebilgin.boot.jps.core.model;

public record Address(
        String street,
        String suite,
        String city,
        String zipCode,
        Geo geo
) {

}
