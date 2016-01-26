package com.ghb.coltpath.model;

import lombok.*;

import javax.persistence.Entity;

/**
 * Created by agheboianu on 25.01.2016.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Project extends AuditModel {

    private String name;
}
