package com.ghb.coltpath.dto;

import com.ghb.coltpath.validators.EmailExists;
import com.ghb.coltpath.validators.UserExists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ghebo on 1/16/2016.
 */

@Getter
@Setter
@ToString
public class RegisterUserPost {
    @NotNull(message = "error.field.empty")
    @Size(min = 1, message = "error.field.empty")
    @EmailExists(exists = false, message = "error.user.exists")
    private String email;
    @NotNull(message = "error.field.empty")
    @Size(min = 6, max = 15, message = "error.field.empty")
    @UserExists(exists = false, message = "error.user.exists")
    private String login;
    @NotNull(message = "error.field.empty")
    @Size(min = 8, message = "error.field.size")
    private String password;
    @NotNull(message = "error.field.empty")
    private String firstName;
    @NotNull(message = "error.field.empty")
    private String lastName;
}
