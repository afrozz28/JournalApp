package net.springProject.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.springProject.journalApp.api.response.weatherResponse;
import net.springProject.journalApp.dto.UserLogin;
import net.springProject.journalApp.entity.User;
import net.springProject.journalApp.service.UserService;
import net.springProject.journalApp.service.weatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "Read, Update & Delete User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private weatherService weatherService;

    @Operation(summary = "Check weather", description = "Make sure you authorized")
    @GetMapping("/weatherInfo/{city}")
    public ResponseEntity<?> greeting(@PathVariable String city){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        weatherResponse weatherResponse = weatherService.getWeather(city);
        String greething = "";
        if (weatherResponse != null){
            greething = ", Weather feels like " +weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greething ,HttpStatus.OK);
    }

    @Operation(summary = "Update user information", description = "Make sure you authorized")
    @PutMapping("/update-user")
    public ResponseEntity<?> update(@RequestBody UserLogin newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findByUserName(username);
        userInDb.setUserName(newEntry.getUserName());
        userInDb.setPassword(newEntry.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(true,HttpStatus.OK);

    }

    @Operation(summary = "Delete User", description = "Make sure you authorized")
    @DeleteMapping
    public ResponseEntity<?> deleteByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(true,HttpStatus.NO_CONTENT);
    }
}
