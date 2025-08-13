package com.spring.objectstore.responsePersonalized;

import java.io.Serializable;

public class ResponsePersonalizedException  implements Serializable {

    private int statusCode;
    private Object message;

    public ResponsePersonalizedException() {
    }

    public ResponsePersonalizedException(int statusCode, Object message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getMessage() {
        return message;
    }
}