package com.harold.automation.tests;

import com.harold.automation.models.Booking;
import com.harold.automation.models.BookingResponse;
import com.harold.automation.services.BookingApi;
import com.harold.automation.utils.TestDataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateBookingTest {

    private final BookingApi bookingApi = new BookingApi();

    @Test
    void shouldCreateBookingSuccessfully() {

        Booking expectedBooking =
                TestDataFactory.validBooking();

        Response response =
                bookingApi.createBooking(expectedBooking);

        response.then()
                .log()
                .ifValidationFails()
                .statusCode(200);

        BookingResponse bookingResponse =
                response.as(BookingResponse.class);

        assertNotNull(bookingResponse.getBookingid());
        assertTrue(bookingResponse.getBookingid() > 0);

        Booking actualBooking =
                bookingResponse.getBooking();

        assertEquals(
                expectedBooking.getFirstname(),
                actualBooking.getFirstname()
        );

        assertEquals(
                expectedBooking.getLastname(),
                actualBooking.getLastname()
        );

        assertEquals(
                expectedBooking.getTotalprice(),
                actualBooking.getTotalprice()
        );

        assertEquals(
                expectedBooking.getDepositpaid(),
                actualBooking.getDepositpaid()
        );

        assertEquals(
                expectedBooking.getBookingdates(),
                actualBooking.getBookingdates()
        );

        assertEquals(
                expectedBooking.getAdditionalneeds(),
                actualBooking.getAdditionalneeds()
        );
    }
}