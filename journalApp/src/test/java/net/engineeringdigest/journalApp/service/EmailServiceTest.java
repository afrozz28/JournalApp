package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testMail(){
        emailService.sendEmail(
                "shirgireswapnali@gmail.com",
                "Testing mail sender",
                "Hello kamchor"
        );
    }
}
