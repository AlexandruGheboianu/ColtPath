package com.ghb.coltpath.dto;

import com.ghb.coltpath.validators.FieldMatch;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ghebo on 1/15/2016.
 */
@Getter
@Setter
@ToString
@FieldMatch(first = "newPassword", second = "passwordConfirm", message = "error.passwords.match")
public class PasswordChangePost {
    @NotNull
    private String oldPassword;
    @NotNull
    @Size(min = 8, message = "error.field.size")
    private String newPassword;
    @NotNull
    @Size(min = 8, message = "error.field.size")
    private String passwordConfirm;
}
