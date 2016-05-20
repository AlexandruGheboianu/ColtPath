package com.ghb.coltpath.admin.dto;

import com.ghb.coltpath.core.validators.UserExists;
import com.ghb.coltpath.core.validators.EmailExists;
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
public class StudentPost {
    @NotNull(message = "error.field.empty")
    @Size(min = 1, message = "error.field.empty")
    @EmailExists(exists = false, message = "error.user.exists")
    private String email;
    @NotNull(message = "error.field.empty")
    @Size(min = 4, max = 15, message = "error.field.empty")
    @UserExists(exists = false, message = "error.user.exists")
    private String login;
    private String password;
    @NotNull(message = "error.field.empty")
    private String firstName;
    @NotNull(message = "error.field.empty")
    private String lastName;
}
