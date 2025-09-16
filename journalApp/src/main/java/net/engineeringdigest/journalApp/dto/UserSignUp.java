package net.engineeringdigest.journalApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUp {

    @NotEmpty
    @Schema(description = "The User's username")
    private String userName;
    @Schema(description = "Required name@gamil.com")
    private String email;
    private boolean sentimentAnalysis;
    @NotEmpty
    private String password;
}
