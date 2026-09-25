package com.harold.automation.services;

import com.harold.automation.config.EnvironmentConfig;
import com.harold.automation.models.AuthRequest;
import com.harold.automation.models.AuthResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthApi {

    public Response authenticate(AuthRequest authRequest) {
        return given()
                .spec(EnvironmentConfig.defaultRequestSpecification())
                .body(authRequest)
                .log()
                .ifValidationFails()
                .when()
                .post("/auth");
    }

    public String generateToken(AuthRequest authRequest) {
        Response response = authenticate(authRequest);

        response.then()
                .log()
                .ifValidationFails()
                .statusCode(200);

        AuthResponse authResponse =
                response.as(AuthResponse.class);

        if (authResponse.getToken() == null
                || authResponse.getToken().isBlank()) {

            throw new IllegalStateException(
                    "La API no devolvió un token válido"
            );
        }

        return authResponse.getToken();
    }
}