package com.spring.objectstore.exceptions;

import com.spring.objectstore.responsePersonalized.ResponsePersonalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RequestValidation {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Object> validaCamposNulosOuVazio(MethodArgumentNotValidException exception) {
        Map<String, Object> camposVazios = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(erro -> camposVazios.put(erro.getField(), erro.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePersonalizedException(HttpStatus.BAD_REQUEST.value(), camposVazios));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    private ResponseEntity<Object> validaCamposNulosOuVazio(MissingServletRequestPartException exception) {
        Map<String, Object> camposVazios = new HashMap<>();
        camposVazios.put(exception.getRequestPartName(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePersonalizedException(HttpStatus.BAD_REQUEST.value(), camposVazios));
    }

    @ExceptionHandler(StorageBadRequest.class)
    public ResponseEntity<Object> storage400(StorageBadRequest exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePersonalizedException(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(StorageNotFound.class)
    public ResponseEntity<Object> storage404(StorageNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePersonalizedException(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

}
