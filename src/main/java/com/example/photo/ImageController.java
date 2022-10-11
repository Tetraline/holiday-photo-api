package com.example.photo;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/image")
    public ResponseEntity addImage(
            @RequestParam("data") MultipartFile imageData,
            @RequestParam("holidayId") String holidayId,
            @RequestParam("locationText") String locationText,
            @RequestParam("description") String description
    ) {
        String message = null;
        try {
            Image i = new Image(Integer.parseInt(holidayId), imageData.getBytes(), locationText, description);
            message = imageService.addImage(i);
        } catch (Exception e) {
            String messageJSON = "{\"message\":\"" + e.getMessage() + "\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageJSON);
        }
        String messageJSON = "{\"message\":\"" + message + "\"}";
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(messageJSON);
    }

    @GetMapping("/image")
    public ResponseEntity getAllImages() {
        List<ResponseImage> images = imageService.getAllImages().map(i -> {
            String downloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/image/")
                    .path(i.getId())
                    .toUriString();
            return new ResponseImage(downloadUri, i.getHolidayId(), i.getLocationText(), i.getDescription());

        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity getImage(@PathVariable String id) {
        Image i = imageService.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).body(i.getData());
        //return ResponseEntity.ok()
                // This line is needed if you want to directly link to the file download
                // The file extension is needed, e.g. jpg:
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + i.getId() + ".jpg\"")
         //       .body(i.getData());
    }

    @GetMapping("/holidays")
    public ResponseEntity getHolidays() {
        List<Integer> holidayIds = imageService.getAllImages().map(i -> i.getHolidayId()).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new HashSet(holidayIds));
    }

    @GetMapping("/holiday/{id}")
    public ResponseEntity getHoliday(@PathVariable String id) {
        List<ResponseImage> images = imageService.getAllImages()
                .filter(i -> i.getHolidayId() == Integer.parseInt(id))
                .map(i -> {
                    String downloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/image/")
                            .path(i.getId())
                            .toUriString();
                    return new ResponseImage(downloadUri, i.getHolidayId(), i.getLocationText(), i.getDescription());

                }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(images);

    }
}
