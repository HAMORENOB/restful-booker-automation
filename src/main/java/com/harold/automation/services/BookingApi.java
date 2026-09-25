package com.harold.automation.services;

import com.harold.automation.config.EnvironmentConfig;
import com.harold.automation.filters.AuthenticationFilter;
import com.harold.automation.models.Booking;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingApi {

    public Response createBooking(Booking booking) {
        return given()
                .spec(EnvironmentConfig.defaultRequestSpecification())
                .body(booking)
                .log()
                .ifValidationFails()
                .when()
                .post("/booking");
    }

    public Response getBookingById(int bookingId) {
        return given()
                .spec(EnvironmentConfig.defaultRequestSpecification())
                .pathParam("bookingId", bookingId)
                .log()
                .ifValidationFails()
                .when()
                .get("/booking/{bookingId}");
    }

    public Response getAllBookings() {
        return given()
                .spec(EnvironmentConfig.defaultRequestSpecification())
                .log()
                .ifValidationFails()
                .when()
                .get("/booking");
    }

    public Response updateBooking(
            int bookingId,
            Booking booking,
            String token) {

        return given()
                .spec(EnvironmentConfig.defaultRequestSpecification())
                .filter(new AuthenticationFilter(token))
                .pathParam("bookingId", bookingId)
                .body(booking)
                .log()
                .ifValidationFails()
                .when()
                .put("/booking/{bookingId}");
    }

    public Response updateBookingWithoutToken(
            int bookingId,
            Booking booking) {

        return given()
                .spec(EnvironmentConfig.defaultRequestSpecification())
                .pathParam("bookingId", bookingId)
                .body(booking)
                .log()
                .ifValidationFails()
                .when()
                .put("/booking/{bookingId}");
    }

    public Response deleteBooking(
            int bookingId,
            String token) {

        return given()
                .spec(EnvironmentConfig.defaultRequestSpecification())
                .filter(new AuthenticationFilter(token))
                .pathParam("bookingId", bookingId)
                .log()
                .ifValidationFails()
                .when()
                .delete("/booking/{bookingId}");
        }
        public Response getBookingsByIds(String bookingIds) {
            return given()
                    .spec(EnvironmentConfig.defaultRequestSpecification())
                    .queryParam("ids", bookingIds)
                    .log()
                    .ifValidationFails()
                    .when()
                    .get("/booking");
    }
}