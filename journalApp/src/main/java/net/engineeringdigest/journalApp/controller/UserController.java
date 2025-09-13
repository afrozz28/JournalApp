package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.response.weatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.weatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private weatherService weatherService;

    @GetMapping
    public ResponseEntity<?> gretting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        weatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greething = "";
        if (weatherResponse != null){
            greething = ", Weather feels like " +weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greething ,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody User newEntry){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User userInDb = userService.findByUserName(username);
            userInDb.setUserName(newEntry.getUserName());
            userInDb.setPassword(newEntry.getPassword());
            userService.saveNewUser(userInDb);
            return new ResponseEntity<>(true,HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(true,HttpStatus.NO_CONTENT);
    }
}
