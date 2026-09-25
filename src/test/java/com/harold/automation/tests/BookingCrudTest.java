package com.harold.automation.tests;

import com.harold.automation.models.Booking;
import com.harold.automation.models.BookingResponse;
import com.harold.automation.utils.TestDataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookingCrudTest extends BaseTest {

    @Test
    void shouldCreateReadUpdateAndDeleteBooking() {

        String token = generateToken();
        Integer bookingId = null;

        try {
            BookingResponse createdBooking = createBooking();
            bookingId = createdBooking.getBookingid();

            assertNotNull(bookingId);

            Response getResponse =
                    bookingApi.getBookingById(bookingId);

            getResponse.then()
                    .log()
                    .ifValidationFails()
                    .statusCode(200);

            Booking retrievedBooking =
                    getResponse.as(Booking.class);

            assertEquals(
                    createdBooking.getBooking().getFirstname(),
                    retrievedBooking.getFirstname()
            );

            Booking updatedBooking =
                    TestDataFactory.validBooking();

            Response updateResponse =
                    bookingApi.updateBooking(
                            bookingId,
                            updatedBooking,
                            token
                    );

            updateResponse.then()
                    .log()
                    .ifValidationFails()
                    .statusCode(200);

            Booking updateResult =
                    updateResponse.as(Booking.class);

            assertEquals(
                    updatedBooking.getFirstname(),
                    updateResult.getFirstname()
            );

            assertEquals(
                    updatedBooking.getLastname(),
                    updateResult.getLastname()
            );

            assertEquals(
                    updatedBooking.getTotalprice(),
                    updateResult.getTotalprice()
            );

        } finally {
            deleteBookingIfExists(bookingId, token);
        }
    }
}