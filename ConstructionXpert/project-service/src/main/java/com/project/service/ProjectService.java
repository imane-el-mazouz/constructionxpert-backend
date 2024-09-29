package com.project.service;

import com.project.exception.ProjectNotFoundException;
import com.project.model.Project;
import com.project.repository.ProjectRepository;
import com.project.repository.ProjectSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
//    private final TaskClient taskClient;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project updateProject (Long id, Project projectDetails) throws ProjectNotFoundException {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setStartDate(projectDetails.getStartDate());
        project.setEndDate(projectDetails.getEndDate());
        project.setBudget(projectDetails.getBudget());
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
//        taskClient.deleteTask(id);
        projectRepository.deleteById(id);
        ResponseEntity.ok().build();
    }

    public Optional<Project> getProjectById(Long id){
        return projectRepository.findById(id);
    }

    public Boolean existProject(Long id) {
        return projectRepository.findById(id).isPresent();
    }

    public Page<Project> getFilteredProjects(Double minBudget, Double maxBudget, Date startDate, Date endDate, Pageable pageable) {
        return projectRepository.findAll(ProjectSpecification.filterByCriteria(minBudget, maxBudget, startDate, endDate), pageable);
    }
}
