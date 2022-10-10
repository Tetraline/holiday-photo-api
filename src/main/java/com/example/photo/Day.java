package com.example.photo;

public class Day {
    int id;
    int holidayId;
    int day;
    String photoFileName;
    String locationText;

    public Day(int id, int holidayId, int day, String photoFileName, String locationText) {
        this.id = id;
        this.holidayId = holidayId;
        this.day = day;
        this.photoFileName = photoFileName;
        this.locationText = locationText;
    }

    public Day(int holidayId, int day, String locationText) {
        this.holidayId = holidayId;
        this.day = day;
        this.locationText = locationText;
    }

    public int getId() {
        return id;
    }

    public int getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }
}
