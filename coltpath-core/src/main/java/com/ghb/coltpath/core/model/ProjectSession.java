package com.ghb.coltpath.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by agheboianu on 20.05.2016.
 */
@Entity
@Getter
@Setter
public class ProjectSession extends AuditModel {

    private Date startDate;
    private Date endDate;
    @ManyToOne
    private Project project;
}
