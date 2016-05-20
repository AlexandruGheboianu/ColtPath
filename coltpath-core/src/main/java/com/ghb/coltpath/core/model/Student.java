package com.ghb.coltpath.core.model;

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

    private String state;

    @PrePersist
    public void checkState() {
        if (state == null || state.isEmpty()) {
            throw new IllegalStateException("Student state cannot be null or empty. It violates the state machine.");
        }
    }
}
