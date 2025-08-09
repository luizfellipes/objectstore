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

    public Storage() {
        this.objectId = UUID.randomUUID();
    }

    public Storage(Integer id, UUID objectId) {
        this.id = id;
        generateNumber(objectId);
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

    private void generateNumber(UUID objectId) {
        if (objectId == null) {
            this.objectId = UUID.randomUUID();
        }
    }
}
