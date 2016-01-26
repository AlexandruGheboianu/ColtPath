package com.ghb.coltpath.dto.reader;

import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * Created by agheboianu on 25.01.2016.
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGet extends ResourceSupport {
    private String name;
    private String createdBy;
    private Date creationDate;
}
