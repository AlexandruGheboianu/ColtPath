package com.ghb.coltpath.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Ghebo on 1/17/2016.
 */
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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
