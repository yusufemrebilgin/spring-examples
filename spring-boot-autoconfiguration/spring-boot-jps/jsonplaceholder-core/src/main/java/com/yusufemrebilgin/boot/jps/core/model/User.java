package com.yusufemrebilgin.boot.jps.core.model;

public record User(
        Integer id,
        String name,
        String username,
        String email,
        Address address,
        String phone,
        String website,
        Company company
) {

}
