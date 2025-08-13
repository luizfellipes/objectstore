package com.spring.objectstore.service;


import com.spring.objectstore.models.dto.StorageDTO;
import com.spring.objectstore.models.entities.Storage;
import com.spring.objectstore.repository.StorageRepository;
import io.minio.*;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
        return Stream.of(convertDtoToModel(storageDTO, file))
                .peek(storage -> {
                    if (file.isEmpty()) {
                        throw new IllegalArgumentException("File cannot be empty");
                    }
                    try {
                        minioClient.putObject(
                                PutObjectArgs.builder()
                                        .bucket("all")
                                        .object(storage.getArchiveName())
                                        .stream(file.getInputStream(), file.getSize(), 0)
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

    public byte[] getArchive(String archiveName) throws Exception {
        return IOUtils.toByteArray(minioClient.getObject(GetObjectArgs.builder()
                .bucket("all")
                .object(archiveName)
                .build()));
    }

    public Storage findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Id not found !"));
    }

    public void deleteById(Integer id) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket("all")
                        .object(findById(id).getObjectId().toString())
                        .build()
        );
        Optional.of(findById(id)).ifPresent(repository::delete);
    }

    private String dateTimer() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss "));
    }

    private Storage convertDtoToModel(StorageDTO DTO, MultipartFile file) {
        return new Storage(DTO.id(), DTO.objectId(), dateTimer() + file.getOriginalFilename());
    }
}

