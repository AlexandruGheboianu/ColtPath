package com.ghb.coltpath.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ghebo on 1/6/2016.
 */
@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("USER")
public abstract class User extends AuditModel {
    @Column(unique = true)
    private String login;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private Date lastLogin;
}
