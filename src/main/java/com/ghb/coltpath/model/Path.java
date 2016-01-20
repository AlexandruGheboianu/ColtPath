package com.ghb.coltpath.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * Created by agheboianu on 20.01.2016.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Path extends AuditModel {

    private String name;
    @ManyToMany
    private Set<User> users;
}
