package com.example.photo;

import java.time.LocalDate;

public class ResponseImage {
    private String url;
    private String holidayId;
    private String locationText;
    private String description;
    private LocalDate date;

    public ResponseImage(String url, String holidayId, String locationText, String description, LocalDate date) {
        this.url = url;
        this.holidayId = holidayId;
        this.locationText = locationText;
        this.description = description;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public String getHolidayId() {
        return holidayId;
    }

    public String getLocationText() {
        return locationText;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }
}
