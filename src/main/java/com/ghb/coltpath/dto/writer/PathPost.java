package com.ghb.coltpath.dto.writer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ghebo on 1/20/2016.
 */
@Getter
@Setter
@ToString
public class PathPost {
    @NotNull(message = "error.field.empty")
    @Size(min = 1, message = "error.field.empty")
    private String name;
}
