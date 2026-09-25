package com.harold.automation.filters;

import io.qameta.allure.restassured.AllureRestAssured;

public final class AllureRestAssuredFilter {

    private AllureRestAssuredFilter() {
    }

    public static AllureRestAssured create() {
        return new AllureRestAssured();
    }
}