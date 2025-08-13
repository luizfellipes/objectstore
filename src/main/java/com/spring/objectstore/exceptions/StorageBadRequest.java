package com.spring.objectstore.exceptions;

public class StorageBadRequest extends IllegalArgumentException {

    public StorageBadRequest() {
    }

    public StorageBadRequest(String s) {
        super(s);
    }
}
