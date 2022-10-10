package com.example.photo;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;


// All endpoints are to return a ResponseEntity
public class PhotoController {

    ArrayList<User> userList = new ArrayList<>();

    ArrayList<Day> dayList = new ArrayList<>();

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @GetMapping("/randomUser")
    public ResponseEntity<User> getRandomUser() {
        Random rand = new Random();
        try {
            int randomIndex = rand.nextInt(userList.size());
            return ResponseEntity.status(HttpStatus.OK).body(userList.get(randomIndex));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/day")
    public ResponseEntity addDay(@RequestBody Day day, @RequestBody MultipartFile file) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        dayList.add(day);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody User user) {
        userList.add(user);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/user")
    public ResponseEntity deleteUser(@PathVariable String id) {
        boolean isDeleted = userList.remove(userList.stream().filter(u -> u.getId() == Integer.parseInt(id)).findFirst().orElse(null));
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
