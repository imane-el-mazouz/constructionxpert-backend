package com.resource.repository;

import com.resource.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> , JpaSpecificationExecutor<Resource> {
    List<Resource> findByTaskId(Long id);

}
