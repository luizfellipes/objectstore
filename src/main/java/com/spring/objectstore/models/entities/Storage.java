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
    private String archiveName;

    public Storage() {
    }

    public Storage(Integer id, UUID objectId, String archiveName) {
        this.id = id;
        generateNumber(objectId);
        this.archiveName = archiveName;
    }

    public Integer getId() {
        return id;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public UUID getObjectId() {
        return objectId;
    }

    private void generateNumber(UUID objectId) {
        this.objectId = (objectId != null) ? objectId : UUID.randomUUID();
    }
}
