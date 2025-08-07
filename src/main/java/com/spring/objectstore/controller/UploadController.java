package com.spring.objectstore.controller;

import com.spring.objectstore.models.dto.StorageDTO;
import com.spring.objectstore.models.entities.Storage;
import com.spring.objectstore.service.StorageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/storage")
public class UploadController {

    private final StorageService service;

    public UploadController(StorageService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Storage> save(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) throws Exception {
        StorageDTO dto = new StorageDTO(id);
        Storage storage = service.save(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(storage);
    }

    @GetMapping
    public ResponseEntity<List<Storage>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
