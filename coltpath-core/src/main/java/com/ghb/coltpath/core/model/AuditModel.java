package com.ghb.coltpath.core.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Ghebo on 1/17/2016.
 */
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString(callSuper = true)
@MappedSuperclass
public abstract class AuditModel extends BaseEntity {


    @CreatedDate
    private Date creationDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private Date lastModification;
    @LastModifiedBy
    private String updatedBy;

}
