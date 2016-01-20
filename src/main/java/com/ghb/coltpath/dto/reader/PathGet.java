package com.ghb.coltpath.dto.reader;

import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * Created by Ghebo on 1/20/2016.
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PathGet extends ResourceSupport {
    private Long pathId;
    private String name;
    private String createdBy;
    private Date creationDate;
}
