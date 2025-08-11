package com.spring.objectstore.models.dto;


import java.util.UUID;

public record StorageDTO(Integer id, UUID objectId, String archiveName) {
}
