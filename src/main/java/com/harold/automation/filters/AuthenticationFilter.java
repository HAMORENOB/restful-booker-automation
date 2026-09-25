package com.harold.automation.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class AuthenticationFilter implements Filter {

    private final String token;

    public AuthenticationFilter(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException(
                    "El token de autenticación es obligatorio"
            );
        }

        this.token = token;
    }

    @Override
    public Response filter(
            FilterableRequestSpecification requestSpecification,
            FilterableResponseSpecification responseSpecification,
            FilterContext filterContext) {

        requestSpecification.cookie("token", token);

        return filterContext.next(
                requestSpecification,
                responseSpecification
        );
    }
}