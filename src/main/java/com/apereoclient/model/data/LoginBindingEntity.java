package com.apereoclient.model.data;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Setter
public class LoginBindingEntity {

    private String username;
    private String password;

    @NotBlank
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters in length!")
    public String getUsername() {
        return username;
    }

    @NotBlank
    public String getPassword() {
        return password;
    }
}
