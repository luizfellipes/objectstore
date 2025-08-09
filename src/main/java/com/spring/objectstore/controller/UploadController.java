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
    public ResponseEntity<Storage> save(StorageDTO storageDTO,  @RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(storageDTO, file));
    }

    @GetMapping("/archiveStorage/{objectId}")
    public ResponseEntity<Object> getArchive(@PathVariable String objectId) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(service.getArchive(objectId));
    }

    @GetMapping
    public ResponseEntity<List<Storage>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
