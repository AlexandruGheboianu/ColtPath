package com.ghb.coltpath.dto.reader;

import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * Created by agheboianu on 20.01.2016.
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserGet extends ResourceSupport {
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private Date lastLogin;
    private String confirmationUrl;

}
