package com.harold.automation.tests;

import com.harold.automation.models.BookingResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator
        .matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.lessThan;

class ContractAndSlaTest extends BaseTest {

    @Test
    void shouldValidateContractSlaAndContentType() {

        String token = generateToken();
        Integer bookingId = null;

        try {
            /*
             * Creamos una reserva para evitar depender
             * de IDs externos o datos temporales.
             */
            BookingResponse createdBooking =
                    createBooking();

            bookingId =
                    createdBooking.getBookingid();

            /*
             * Consultamos la reserva creada.
             */
            Response response =
                    bookingApi.getBookingById(
                            bookingId
                    );

            /*
             * Validamos estado, SLA, Content-Type
             * y contrato JSON.
             */
            response.then()
                    .log()
                    .ifValidationFails()
                    .statusCode(200)
                    .time(
                            lessThan(2000L)
                    )
                    .header(
                            "Content-Type",
                            containsStringIgnoringCase(
                                    "application/json"
                            )
                    )
                    .body(
                            matchesJsonSchemaInClasspath(
                                    "schemas/booking-schema.json"
                            )
                    );

        } finally {
            /*
             * Eliminamos la reserva incluso cuando
             * alguna validación falla.
             */
            deleteBookingIfExists(
                    bookingId,
                    token
            );
        }
    }
}