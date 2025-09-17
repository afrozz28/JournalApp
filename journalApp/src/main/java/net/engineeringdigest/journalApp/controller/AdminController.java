package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.dto.UserSignUp;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs", description = "Handle Users, Create admin user")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @Operation(summary = "Get all users", description = "Make sure you authorized as a admin")
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create admin user", description = "Make sure you authorized as a admin")
    @PostMapping("/create-Admin")
    public ResponseEntity<?> createAdmin(@RequestBody UserSignUp newUser){
        User user = new User();
        user.setUserName(newUser.getUserName());
        user.setSentimentAnalysis(newUser.isSentimentAnalysis());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        userService.saveAdmin(user);
        return new ResponseEntity<>(true,HttpStatus.CREATED);
    }

    @Operation(summary = "Clear weather data from cache", description = "Make sure you authorized as a admin")
    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
