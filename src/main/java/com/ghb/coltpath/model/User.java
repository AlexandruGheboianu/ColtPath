package com.ghb.coltpath.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ghebo on 1/6/2016.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class User extends AuditModel {

    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
    private Date lastLogin;
    private String confirmationUrl;

    public String serializedRoles() {
        String rolesString = "";
        for (Role role : roles) {
            rolesString += role + ",";
        }
        return rolesString.substring(0, rolesString.length());
    }
}
