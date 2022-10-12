package com.example.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private HolidayService holidayService;

    @PostMapping("/image")
    public ResponseEntity<String> addImage(
            @RequestParam("data") MultipartFile imageData,
            @RequestParam("holidayId") String holidayId,
            @RequestParam("locationText") String locationText,
            @RequestParam("description") String description,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        String message = null;
        Holiday holiday = holidayService.getHoliday(holidayId);
        try {
            Image i = new Image(imageData.getBytes(), locationText, description, date, holiday);
            message = imageService.addImage(i);
        } catch (Exception e) {
            String messageJSON = "{\"message\":\"" + e.getMessage() + "\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageJSON);
        }
        String messageJSON = "{\"message\":\"" + message + "\"}";
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(messageJSON);
    }

    @PostMapping("/holiday")
    public ResponseEntity<String> addHoliday(
            @RequestParam("name") String name,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        String message = null;
        try {
            Holiday h = new Holiday(name, startDate, endDate);
            message = holidayService.addHoliday(h);
        } catch (Exception e) {
            String messageJSON = "{\"message\":\"" + e.getMessage() + "\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageJSON);
        }
        String messageJSON = "{\"message\":\"" + message + "\"}";
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(messageJSON);
    }


    @GetMapping("/image")
    public ResponseEntity<List<ResponseImage>> getAllImages() {
        List<ResponseImage> images = imageService.getAllImages().map(this::imageToResponseImage).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        Image i = imageService.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).body(i.getData());
        //return ResponseEntity.ok()
        // This line is needed if you want to directly link to the file download
        // The file extension is needed, e.g. jpg:
        //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + i.getId() + ".jpg\"")
        //       .body(i.getData());
    }

    @GetMapping("/holidays")
    public ResponseEntity<List<Holiday>> getHolidays() {
        List<Holiday> holidays = holidayService.getAllHolidays().collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(holidays);
    }

    @GetMapping("/holiday/{id}")
    public ResponseEntity<Holiday> getHoliday(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(holidayService.getHoliday(id));
    }

    @GetMapping("/holiday-images/{id}")
    public ResponseEntity<List<ResponseImage>> getHolidayImages(@PathVariable String id) {
        List<ResponseImage> images = imageService.getImagesOfHoliday(id).map(this::imageToResponseImage).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    public ResponseImage imageToResponseImage(Image i) {
        String downloadUri;
        if (i.getData().length > 0) {
            downloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/image/")
                    .path(i.getId())
                    .toUriString();
        } else {
            downloadUri = "";
        }
        return new ResponseImage(downloadUri, i.getHolidayId(), i.getLocationText(), i.getDescription(), i.getDate());
    }
}
