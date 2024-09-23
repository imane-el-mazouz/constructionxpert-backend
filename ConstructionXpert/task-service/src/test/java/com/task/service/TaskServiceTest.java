package com.task.service;

import com.task.exception.TaskNotFoundException;
import com.task.model.Task;
import com.task.repository.TaskRepository;
import com.task.feign.ProjectClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectClient projectClient;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        task = new Task();
        task.setId(1L);
        task.setDescription("Test Task");
        task.setStartDate(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        task.setEndDate(LocalDate.parse("25/09/2024", formatter));
        task.setStatus("todo");
        task.setProjectId(100L);
    }

    @Test
    public void testCreateTask() {
        when(projectClient.existProject(anyLong())).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task task1 = taskService.createTask(task);
        assertNotNull(task1);
        assertEquals(task.getId(), task1.getId());
        assertEquals(task.getDescription() , task1.getDescription());
        assertEquals(task.getStartDate(), task1.getStartDate());
        assertEquals(task.getEndDate() , task1.getEndDate());
        assertEquals(task.getStatus() , task1.getStatus());
        assertEquals(task.getProjectId(), task1.getProjectId());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void GetTasksByProjectId() throws TaskNotFoundException {
        when(projectClient.existProject(anyLong())).thenReturn(true);
        when(taskRepository.findByProjectId(anyLong())).thenReturn(Collections.singletonList(task));
        List<Task> tasks = taskService.getTasksByProjectId(100L);
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(task.getProjectId(), tasks.get(0).getProjectId()); // Changed from getFirst() to get(0)
        verify(taskRepository, times(1)).findByProjectId(100L);
    }

    @Test
    public void getTaskByProjectId() {
        when(projectClient.existProject(anyLong())).thenReturn(false);
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTasksByProjectId(100L);
        });
        verify(taskRepository, never()).findByProjectId(anyLong());
    }

    @Test
    public void getAllTasks() {
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));
        List<Task> tasks = taskService.getAllTasks();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(task.getId(), tasks.get(0).getId()); // Changed from getFirst() to get(0)
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void getTaskById() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        Optional<Task> foundTask = taskService.getTaskById(1L);
        assertTrue(foundTask.isPresent());
        assertEquals(task.getId(), foundTask.get().getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void NoTaskFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Task> task1 = taskService.getTaskById(1L);
        assertFalse(task1.isPresent());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void UpdateTask() throws TaskNotFoundException {
        Task updatedTask = new Task();
        updatedTask.setDescription("Updated Description");
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        Task result = taskService.updateTask(1L, updatedTask);
        assertNotNull(result);
        assertEquals("Updated Description", result.getDescription());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void UpdateTaskNotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(1L, task);
        });
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    public void DeleteTask() {
        doNothing().when(taskRepository).deleteById(anyLong());
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void ExistTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        Boolean exists = taskService.existTask(1L);
        assertTrue(exists);
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void TaskNotExist() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());
        Boolean exists = taskService.existTask(1L);
        assertFalse(exists);
        verify(taskRepository, times(1)).findById(1L);
    }
}
