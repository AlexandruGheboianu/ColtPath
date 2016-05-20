package com.ghb.coltpath.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by agheboianu on 20.05.2016.
 */
@Entity
@Getter
@Setter
public class Topic extends BaseEntity {

    private String name;
}
