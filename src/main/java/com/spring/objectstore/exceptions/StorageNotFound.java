package com.spring.objectstore.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class StorageNotFound extends EntityNotFoundException {
    public StorageNotFound() {
    }

    public StorageNotFound(String message) {
        super(message);
    }
}