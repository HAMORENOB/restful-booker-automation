package com.harold.automation.config;

import com.harold.automation.filters.AllureRestAssuredFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class EnvironmentConfig {

    private EnvironmentConfig() {
    }

    public static RequestSpecification
    defaultRequestSpecification() {

        return new RequestSpecBuilder()
                .setBaseUri(
                        ConfigManager.getBaseUrl()
                )
                .setContentType(
                        ContentType.JSON
                )
                .addHeader(
                        "Accept",
                        "application/json"
                )
                .addHeader(
                        "User-Agent",
                        "PostmanRuntime/7.43.0"
                )
                .addFilter(
                        AllureRestAssuredFilter.create()
                )
                .build();
    }
}