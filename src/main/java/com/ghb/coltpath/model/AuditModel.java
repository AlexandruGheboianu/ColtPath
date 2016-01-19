package com.ghb.coltpath.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by Ghebo on 1/17/2016.
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@MappedSuperclass
public abstract class AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private Date creationDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private Date lastModification;
    @LastModifiedBy
    private String updatedBy;
    @Version
    private Long version;
}
