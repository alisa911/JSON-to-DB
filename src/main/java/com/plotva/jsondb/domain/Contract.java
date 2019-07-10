package com.plotva.jsondb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity
public class Contract {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long pk;
    private String hash;
    private String language;
    private String description;
    private String format;
    private String url;
    private String title;
    private String documentOf;
    private Date datePublished;
    private String documentType;
    private Date dateModified;
    private String relatedItem;
    private String id;

    public Contract() {
    }
}
