package com.harold.automation.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harold.automation.models.Booking;
import com.harold.automation.models.BookingResponse;
import com.harold.automation.utils.TestDataFactory;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingJsonPathTest extends BaseTest {

    private static final int PRICE_THRESHOLD = 300;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldFilterBookingsUsingGroovyPathExpressions() {

        String token = generateToken();
        List<Integer> createdBookingIds = new ArrayList<>();

        try {
            /*
             * La API GET /booking devuelve la lista general de IDs.
             * Se consume para cumplir el flujo solicitado.
             */
            Response allBookingsResponse =
                    bookingApi.getAllBookings();

            allBookingsResponse.then()
                    .log()
                    .ifValidationFails()
                    .statusCode(200);

            List<Integer> availableIds =
                    allBookingsResponse.jsonPath()
                            .getList("bookingid", Integer.class);

            assertNotNull(availableIds);
            assertFalse(availableIds.isEmpty());

            /*
             * Creamos reservas controladas para tener datos
             * conocidos y evitar depender de registros externos.
             */
            BookingResponse firstBooking =
                    createBooking(
                            TestDataFactory.bookingWithPrice(200)
                    );

            BookingResponse secondBooking =
                    createBooking(
                            TestDataFactory.bookingWithPrice(450)
                    );

            BookingResponse thirdBooking =
                    createBooking(
                            TestDataFactory.bookingWithPrice(700)
                    );

            createdBookingIds.add(firstBooking.getBookingid());
            createdBookingIds.add(secondBooking.getBookingid());
            createdBookingIds.add(thirdBooking.getBookingid());

            List<Map<String, Object>> detailedBookings =
                    createdBookingIds.stream()
                            .map(bookingId -> {
                                Response response =
                                        bookingApi.getBookingById(
                                                bookingId
                                        );
                                response.then()
                                        .log()
                                        .ifValidationFails()
                                        .statusCode(200);

                                Booking booking =
                                        response.as(Booking.class);
                                return Map.<String, Object>of(
                                        "bookingid", bookingId,
                                        "booking", booking
                                );
                            })
                            .toList();

            String bookingsJson =
                    serializeBookings(detailedBookings);

            JsonPath jsonPath =
                    JsonPath.from(bookingsJson);

            List<Integer> filteredIds =
                    jsonPath.getList(
                            "findAll { " +
                                    "it.booking.totalprice > " +
                                    PRICE_THRESHOLD +
                                    " }.bookingid",
                            Integer.class
                    );

            assertNotNull(filteredIds);

            /*
             * Puede ocurrir que las tres reservas tengan precios
             * iguales o menores al umbral, porque son dinámicos.
             */
            assertTrue(
                    filteredIds.stream()
                            .allMatch(createdBookingIds::contains)
            );

            if (!filteredIds.isEmpty()) {
                Integer selectedId = filteredIds.getFirst();

                Response selectedResponse =
                        bookingApi.getBookingById(selectedId);

                selectedResponse.then()
                        .log()
                        .ifValidationFails()
                        .statusCode(200);

                Booking selectedBooking =
                        selectedResponse.as(Booking.class);

                assertNotNull(selectedBooking);
                assertTrue(
                        selectedBooking.getTotalprice()
                                > PRICE_THRESHOLD
                );
            }

        } finally {
            createdBookingIds.forEach(
                    bookingId ->
                            deleteBookingIfExists(
                                    bookingId,
                                    token
                            )
            );
        }
    }

    private String serializeBookings(
            List<Map<String, Object>> detailedBookings) {

        try {
            return objectMapper.writeValueAsString(
                    detailedBookings
            );
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException(
                    "No fue posible serializar las reservas",
                    exception
            );
        }
    }
}
