package com.spring.objectstore.service;


import com.spring.objectstore.models.dto.StorageDTO;
import com.spring.objectstore.models.entities.Storage;
import com.spring.objectstore.repository.StorageRepository;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Stream;


@Service
public class StorageService {

    private final StorageRepository repository;
    private final MinioClient minioClient;

    public StorageService(StorageRepository repository, MinioClient minioClient) {
        this.repository = repository;
        this.minioClient = minioClient;
    }

    public Storage save(StorageDTO storageDTO, MultipartFile file) {
        return Stream.of(convertDtoToModel(storageDTO))
                .peek(storage -> {
                    if (file.isEmpty()) {
                        throw new IllegalArgumentException("File cannot be empty");
                    }
                    try {
                        minioClient.putObject(
                                io.minio.PutObjectArgs.builder()
                                        .bucket("all")
                                        .object(storage.getObjectId().toString())
                                        .stream(file.getInputStream(), file.getSize(), -1)
                                        .contentType(file.getContentType())
                                        .build()
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(repository::save)
                .findFirst()
                .orElseThrow();
    }

    public List<Storage> getAll() {
        return repository.findAll();
    }

    public Object getArchive(String objectId) throws Exception {
        var stream = minioClient.getObject(GetObjectArgs.builder().bucket("all").object(objectId).build());
        return IOUtils.toByteArray(stream);
    }

    private Storage convertDtoToModel(StorageDTO DTO) {
        return new Storage(DTO.id(), DTO.objectId());
    }
}

