package com.spring.objectstore.models.entities;

import jakarta.persistence.*;


import java.util.UUID;

@Entity
@Table(name = "STORAGE")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private UUID objectId;

    public Storage(Integer id) {
        this.id = id;
        this.objectId = UUID.fromString(UUID.randomUUID().toString());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }
}
