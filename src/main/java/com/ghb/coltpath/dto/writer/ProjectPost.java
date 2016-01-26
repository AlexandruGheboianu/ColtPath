package com.ghb.coltpath.dto.writer;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by agheboianu on 25.01.2016.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPost {
    @NotNull(message = "error.field.empty")
    @Size(min = 1, message = "error.field.empty")
    private String name;
}
