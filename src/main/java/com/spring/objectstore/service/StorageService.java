package com.spring.objectstore.service;


import com.spring.objectstore.models.dto.StorageDTO;
import com.spring.objectstore.models.entities.Storage;
import com.spring.objectstore.repository.StorageRepository;
import io.minio.MinioClient;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;


import java.util.List;

import java.util.UUID;


@Service
public class StorageService {

    private final StorageRepository repository;
    private final MinioClient minioClient;

    public StorageService(StorageRepository repository, MinioClient minioClient) {
        this.repository = repository;
        this.minioClient = minioClient;
    }

    public Storage save(StorageDTO storageDTO, MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        UUID objectId = UUID.randomUUID();
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    io.minio.PutObjectArgs.builder()
                            .bucket("all")
                            .object(objectId.toString())
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }

        Storage storage = new Storage(storageDTO.id());
        storage.setObjectId(objectId);

        return repository.save(storage);
    }

    public List<Storage> getAll() {
        return repository.findAll();
    }
}

