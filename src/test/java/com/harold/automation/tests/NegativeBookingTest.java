package com.harold.automation.tests;

import com.harold.automation.models.Booking;
import com.harold.automation.models.BookingResponse;
import com.harold.automation.utils.TestDataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsStringIgnoringCase;

class NegativeBookingTest extends BaseTest {

    private static final int NON_EXISTING_BOOKING_ID =
            9_999_999;

    @Test
    void shouldReturnForbiddenWhenUpdatingWithoutToken() {

        String cleanupToken = generateToken();
        Integer bookingId = null;

        try {
            /*
             * Primero creamos una reserva válida para garantizar
             * que el recurso que intentaremos actualizar existe.
             */
            BookingResponse createdBooking =
                    createBooking();

            bookingId =
                    createdBooking.getBookingid();

            /*
             * Construimos nuevos datos para la actualización.
             */
            Booking updatedBooking =
                    TestDataFactory.validBooking();

            /*
             * Ejecutamos PUT sin enviar token o cookie.
             */
            Response response =
                    bookingApi.updateBookingWithoutToken(
                            bookingId,
                            updatedBooking
                    );

            /*
             * La API debe rechazar la actualización.
             */
            response.then()
                    .log()
                    .ifValidationFails()
                    .statusCode(403)
                    .body(
                            containsStringIgnoringCase(
                                    "Forbidden"
                            )
                    );

        } finally {
            /*
             * El token se usa exclusivamente para limpiar
             * la reserva después de la prueba.
             */
            deleteBookingIfExists(
                    bookingId,
                    cleanupToken
            );
        }
    }

    @Test
    void shouldReturnNotFoundForNonExistingBooking() {

        Response response =
                bookingApi.getBookingById(
                        NON_EXISTING_BOOKING_ID
                );

        response.then()
                .log()
                .ifValidationFails()
                .statusCode(404);
    }
}