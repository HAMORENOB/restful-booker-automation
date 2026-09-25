package com.harold.automation.utils;

import com.harold.automation.config.ConfigManager;
import com.harold.automation.models.AuthRequest;
import com.harold.automation.models.Booking;
import com.harold.automation.models.BookingDates;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static AuthRequest validAuthentication() {
        return AuthRequest.builder()
                .username(ConfigManager.getUsername())
                .password(ConfigManager.getPassword())
                .build();
    }

    public static Booking validBooking() {
        LocalDate checkin = LocalDate.now().plusDays(5);
        LocalDate checkout = checkin.plusDays(5);

        return Booking.builder()
                .firstname("Harold-" + shortIdentifier())
                .lastname("Moreno-" + shortIdentifier())
                .totalprice(randomPrice())
                .depositpaid(true)
                .bookingdates(
                        BookingDates.builder()
                                .checkin(checkin.toString())
                                .checkout(checkout.toString())
                                .build()
                )
                .additionalneeds("Breakfast")
                .build();
    }

    public static Booking bookingWithPrice(int totalPrice) {
        LocalDate checkin = LocalDate.now().plusDays(5);
        LocalDate checkout = checkin.plusDays(5);

        return Booking.builder()
                .firstname("Harold-" + shortIdentifier())
                .lastname("Moreno-" + shortIdentifier())
                .totalprice(totalPrice)
                .depositpaid(true)
                .bookingdates(
                        BookingDates.builder()
                                .checkin(checkin.toString())
                                .checkout(checkout.toString())
                                .build()
                )
                .additionalneeds("Breakfast")
                .build();
    }

    private static int randomPrice() {
        return ThreadLocalRandom.current()
                .nextInt(100, 1001);
    }

    private static String shortIdentifier() {
        return UUID.randomUUID()
                .toString()
                .substring(0, 8);
    }
}