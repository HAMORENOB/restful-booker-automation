package com.harold.automation.tests;

import com.harold.automation.models.Booking;
import com.harold.automation.models.BookingDates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelBuilderTest {

    @Test
    void shouldBuildBookingSuccessfully() {

        BookingDates bookingDates = BookingDates.builder()
                .checkin("2026-10-01")
                .checkout("2026-10-10")
                .build();

        Booking booking = Booking.builder()
                .firstname("Harold")
                .lastname("Moreno")
                .totalprice(350)
                .depositpaid(true)
                .bookingdates(bookingDates)
                .additionalneeds("Breakfast")
                .build();

        assertEquals("Harold", booking.getFirstname());
        assertEquals("Moreno", booking.getLastname());
        assertEquals(350, booking.getTotalprice());
        assertTrue(booking.getDepositpaid());
        assertEquals("2026-10-01",
                booking.getBookingdates().getCheckin());
    }
}