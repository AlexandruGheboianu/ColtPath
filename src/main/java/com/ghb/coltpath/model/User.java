package com.ghb.coltpath.model;

import lombok.*;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @Column(unique = true)
    private String login;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    private Date lastLogin;
    @Column(nullable = false)
    private String state;

    public String serializedRoles() {
        String rolesString = "";
        for (Role role : roles) {
            rolesString += role.getName() + ",";
        }
        return rolesString.substring(0, rolesString.length());
    }
}
