package com.harold.automation.models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BookingDates {

    private String checkin;
    private String checkout;
}