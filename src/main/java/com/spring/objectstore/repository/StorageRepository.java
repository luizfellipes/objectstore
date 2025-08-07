package com.spring.objectstore.repository;

import com.spring.objectstore.models.entities.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {

}
