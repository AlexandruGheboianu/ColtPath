package com.ghb.coltpath.dto.writer;

import com.ghb.coltpath.validators.UserIdExists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by agheboianu on 22.01.2016.
 */
@Getter
@Setter
@ToString
public class PathUsersPost {

    @NotNull(message = "error.missing.user")
    @UserIdExists(exists = true, message = "error.missing.user")
    private Set<Long> userIds;
}
