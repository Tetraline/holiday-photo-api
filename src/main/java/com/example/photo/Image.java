package com.example.photo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Lob
    private byte[] data;
    private String locationText;
    @Lob
    @Column( length = 100000 )
    private String description;
    private LocalDate date;
    @JsonIgnore // Stop the holiday being sent with every image
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "holiday_id", nullable = false)
    private Holiday holiday;

    public Image() {
    }

    public Image(byte[] data, String locationText, String description, LocalDate date, Holiday holiday)  {
        this.data = data;
        this.locationText = locationText;
        this.description = description;
        this.date = date;
        this.holiday = holiday;
    }

    public Holiday getHoliday() {
        return holiday;
    }

    public String getHolidayId(){
        return holiday.getId();
    }
    public void setHoliday(Holiday holiday) {
        this.holiday = holiday;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
