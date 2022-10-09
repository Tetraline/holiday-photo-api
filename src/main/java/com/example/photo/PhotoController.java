package com.example.photo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Random;


// All endpoints are to return a ResponseEntity
@RestController
public class PhotoController {

    ArrayList<User> userList = new ArrayList<>();

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
