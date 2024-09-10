package com.task.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "project-service")
public interface ProjectClient {

    @GetMapping("/api/projects/{id}/exist")
    Boolean existProject(@PathVariable("id") Long id);


}
