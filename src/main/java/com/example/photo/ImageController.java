package com.example.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + i.getId() + ".jpg\"")
                .body(i.getData());
    }
}
