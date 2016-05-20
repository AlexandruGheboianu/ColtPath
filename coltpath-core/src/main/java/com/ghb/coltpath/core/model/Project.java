package com.ghb.coltpath.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by agheboianu on 25.01.2016.
 */
@Entity
@Getter
@Setter
@ToString(callSuper = true)
public class Project extends AuditModel {

    private String name;

    private String subjectLink;

    @ManyToMany
    List<Topic> projectTopics;
}
