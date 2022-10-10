package com.example.photo;

public class ResponseImage {
    private String url;
    private int holidayId;
    private String locationText;
    private String description;

    public ResponseImage(String url, int holidayId, String locationText, String description) {
        this.url = url;
        this.holidayId = holidayId;
        this.locationText = locationText;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public int getHolidayId() {
        return holidayId;
    }

    public String getLocationText() {
        return locationText;
    }

    public String getDescription() {
        return description;
    }
}
