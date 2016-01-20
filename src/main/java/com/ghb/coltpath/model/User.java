package com.ghb.coltpath.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.*;

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
    private Set<Role> roles = new HashSet<>();
    private Date lastLogin;
    private String confirmationUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Path> paths;

    public String serializedRoles() {
        String rolesString = "";
        for (Role role : roles) {
            rolesString += role.getName() + ",";
        }
        return rolesString.substring(0, rolesString.length());
    }
}
