package me.personal.myflix.payload.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
