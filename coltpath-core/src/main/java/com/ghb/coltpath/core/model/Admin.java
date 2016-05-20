package com.ghb.coltpath.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by agheboianu on 20.05.2016.
 */

@Entity
@DiscriminatorValue("ADMIN")
@Getter
@Setter
public class Admin extends User {
}
