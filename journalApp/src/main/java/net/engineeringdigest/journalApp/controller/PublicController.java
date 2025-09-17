package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.dto.UserLogin;
import net.engineeringdigest.journalApp.dto.UserSignUp;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
@Tag(name = "Public APIs", description = "Allow everyone to hit API authorization is not required")
public class PublicController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Check for response")
    @GetMapping("/health-care")
    public String healthcare() {
        return ("ok");
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignUp myEntry){
        User newUser = new User();
        newUser.setEmail(myEntry.getEmail());
        newUser.setUserName(myEntry.getUserName());
        newUser.setSentimentAnalysis(myEntry.isSentimentAnalysis());
        newUser.setPassword(myEntry.getPassword());
        userService.saveNewUser(newUser);
        return  new ResponseEntity<>(true, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLogin myEntry){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            myEntry.getUserName(),
                            myEntry.getPassword()
                    ));
            UserDetails userDetails = userDetailsService.loadUserByUsername(myEntry.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (AuthenticationException e) {
            log.error("Exception occurred while createAuthenticationToken ",e);
            return  new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
