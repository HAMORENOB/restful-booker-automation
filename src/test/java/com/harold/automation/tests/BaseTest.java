package com.harold.automation.tests;

import com.harold.automation.models.AuthRequest;
import com.harold.automation.models.Booking;
import com.harold.automation.models.BookingResponse;
import com.harold.automation.services.AuthApi;
import com.harold.automation.services.BookingApi;
import com.harold.automation.utils.TestDataFactory;
import io.restassured.response.Response;

public abstract class BaseTest {

    protected final AuthApi authApi = new AuthApi();
    protected final BookingApi bookingApi = new BookingApi();

    protected String generateToken() {
        AuthRequest authRequest =
                TestDataFactory.validAuthentication();

        return authApi.generateToken(authRequest);
    }

    protected BookingResponse createBooking() {
        Booking booking =
                TestDataFactory.validBooking();

        return createBooking(booking);
    }

    protected BookingResponse createBooking(
            Booking booking) {

        Response response =
                bookingApi.createBooking(booking);

        response.then()
                .log()
                .ifValidationFails()
                .statusCode(200);

        return response.as(BookingResponse.class);
    }

    protected void deleteBookingIfExists(
            Integer bookingId,
            String token) {

        if (bookingId == null || token == null) {
            return;
        }

        Response response =
                bookingApi.deleteBooking(
                        bookingId,
                        token
                );

        if (response.statusCode() != 201
                && response.statusCode() != 204) {

            System.out.println(
                    "No fue posible eliminar la reserva "
                            + bookingId
                            + ". Status: "
                            + response.statusCode()
            );
        }
    }
}