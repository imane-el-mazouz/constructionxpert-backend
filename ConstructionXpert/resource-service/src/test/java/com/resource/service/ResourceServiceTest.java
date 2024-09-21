package com.resource.service;

import com.resource.exception.ResourceNotFoundException;
import com.resource.feign.TaskClient;
import com.resource.model.Resource;
import com.resource.repository.ResourceRepository;
import com.resource.enums.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private TaskClient taskClient;

    @InjectMocks
    private ResourceService resourceService;

    private Resource testResource;

    @BeforeEach
    void setUp() {
        testResource = new Resource(1L, "Test Resource", 10, ResourceType.MATERIAL, "Test Provider", 1L);
    }

    @Test
    void testCreateResource_Success() {
        when(taskClient.existTask(1L)).thenReturn(true);
        when(resourceRepository.save(any(Resource.class))).thenReturn(testResource);

        Resource result = resourceService.createResource(testResource);

        assertNotNull(result);
        assertEquals(testResource.getName(), result.getName());
        verify(taskClient).existTask(1L);
        verify(resourceRepository).save(testResource);
    }

    @Test
    void testCreateResource_TaskNotFound() {
        when(taskClient.existTask(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> resourceService.createResource(testResource));
        verify(taskClient).existTask(1L);
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    @Test
    void testCreateResource_NullTaskId() {
        testResource.setTaskId(null);

        assertThrows(IllegalArgumentException.class, () -> resourceService.createResource(testResource));
        verify(taskClient, never()).existTask(anyLong());
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    @Test
    void testGetResourcesByTaskId_Success() throws ResourceNotFoundException {
        List<Resource> resources = Collections.singletonList(testResource);
        when(taskClient.existTask(1L)).thenReturn(true);
        when(resourceRepository.findByTaskId(1L)).thenReturn(resources);

        List<Resource> result = resourceService.getResourcesByTaskId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testResource.getName(), result.getFirst().getName());
        verify(taskClient).existTask(1L);
        verify(resourceRepository).findByTaskId(1L);
    }

    @Test
    void testGetResourcesByTaskId_TaskNotFound() {
        when(taskClient.existTask(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> resourceService.getResourcesByTaskId(1L));
        verify(taskClient).existTask(1L);
        verify(resourceRepository, never()).findByTaskId(anyLong());
    }

    @Test
    void testGetAllResources() {
        List<Resource> resources = Collections.singletonList(testResource);
        when(resourceRepository.findAll()).thenReturn(resources);

        List<Resource> result = resourceService.getAllResources();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testResource.getName(), result.getFirst().getName());
        verify(resourceRepository).findAll();
    }

    @Test
    void testUpdateResource_Success() throws ResourceNotFoundException {
        Resource updatedResource = new Resource(1L, "Updated Resource", 20, ResourceType.MATERIAL, "Updated Provider", 1L);
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(testResource));
        when(resourceRepository.save(any(Resource.class))).thenReturn(updatedResource);

        Resource result = resourceService.updateResource(1L, updatedResource);

        assertNotNull(result);
        assertEquals(updatedResource.getName(), result.getName());
        assertEquals(updatedResource.getQuantity(), result.getQuantity());
        assertEquals(updatedResource.getType(), result.getType());
        assertEquals(updatedResource.getProvider(), result.getProvider());
        verify(resourceRepository).findById(1L);
        verify(resourceRepository).save(any(Resource.class));
    }

    @Test
    void testUpdateResource_NotFound() {
        when(resourceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> resourceService.updateResource(1L, testResource));
        verify(resourceRepository).findById(1L);
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    @Test
    void testDeleteResource() {
        doNothing().when(resourceRepository).deleteById(1L);

        resourceService.deleteResource(1L);

        verify(resourceRepository).deleteById(1L);
    }

    @Test
    void testFindById_Success() {
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(testResource));

        Resource result = resourceService.findById(1L);

        assertNotNull(result);
        assertEquals(testResource.getName(), result.getName());
        verify(resourceRepository).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(resourceRepository.findById(1L)).thenReturn(Optional.empty());

        Resource result = resourceService.findById(1L);

        assertNull(result);
        verify(resourceRepository).findById(1L);
    }
}