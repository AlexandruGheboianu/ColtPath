package com.ghb.coltpath.elearning.dto.writer;

import com.ghb.coltpath.elearning.validators.UserExists;
import com.ghb.coltpath.elearning.validators.EmailExists;
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
public class UserPost {
    @NotNull(message = "error.field.empty")
    @Size(min = 1, message = "error.field.empty")
    @EmailExists(exists = false, message = "error.user.exists")
    private String email;
    @NotNull(message = "error.field.empty")
    @Size(min = 4, max = 15, message = "error.field.empty")
    @UserExists(exists = false, message = "error.user.exists")
    private String login;
    @NotNull(message = "error.field.empty")
    @Size(min = 4, message = "error.field.size")
    private String password;
    @NotNull(message = "error.field.empty")
    private String firstName;
    @NotNull(message = "error.field.empty")
    private String lastName;
}
