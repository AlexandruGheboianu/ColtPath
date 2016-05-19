package com.ghb.coltpath.elearning.model;

import com.ghb.coltpath.core.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by agheboianu on 19.05.2016.
 */

@Entity
@DiscriminatorValue("STUDENT")
@Getter
@Setter
public class Student extends User {
    @Column(nullable = false)
    private String state;

}
