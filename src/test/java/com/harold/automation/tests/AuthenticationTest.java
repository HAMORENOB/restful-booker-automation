package com.harold.automation.tests;

import com.harold.automation.models.AuthRequest;
import com.harold.automation.services.AuthApi;
import com.harold.automation.utils.TestDataFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthenticationTest {

    private final AuthApi authApi = new AuthApi();

    @Test
    void shouldGenerateValidAuthenticationToken() {

        AuthRequest authRequest =
                TestDataFactory.validAuthentication();

        String token = authApi.generateToken(authRequest);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }
}