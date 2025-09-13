package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-care")
    public String healthcare() {
        return ("ok");
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody User myEntry){
        userService.saveNewUser(myEntry);
        return  new ResponseEntity<>(true, HttpStatus.CREATED);
    }
}
