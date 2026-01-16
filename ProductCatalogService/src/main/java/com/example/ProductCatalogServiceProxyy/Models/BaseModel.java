package com.example.ProductCatalogServiceProxyy.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Status status;

//    @Column(nullable = false, updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt = new Date();
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt = new Date();
//
//    @Enumerated(EnumType.ORDINAL)
//    @Column(nullable = false)
//    private Status status = Status.Active;
}
