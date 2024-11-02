package mathapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import mathapp.DTO.TaskDTO;
import mathapp.model.FunctionEntity;
import mathapp.model.TaskEntity;
import mathapp.repository.FunctionRepository;
import mathapp.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private FunctionRepository functionRepository;

    private TaskDTO taskDTO;
    private TaskEntity taskEntity1;
    private TaskEntity taskEntity2;
    private FunctionEntity functionEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        functionEntity = new FunctionEntity();
        functionEntity.setFunctionId(1L);

        taskDTO = new TaskDTO();
        taskDTO.setTaskId(1L);
        taskDTO.setFunctionId(1L);
        taskDTO.setTaskType("Task");
        taskDTO.setStatus("Pending");
        taskDTO.setStartTime(LocalDateTime.now());
        taskDTO.setEndTime(LocalDateTime.now().plusHours(1));

        taskEntity1 = new TaskEntity();
        taskEntity1.setTaskId(1L);
        taskEntity1.setFunction(functionEntity);
        taskEntity1.setTaskType("Task");
        taskEntity1.setStatus("Pending");
        taskEntity1.setStartTime(taskDTO.getStartTime());
        taskEntity1.setEndTime(taskDTO.getEndTime());

        taskEntity2 = new TaskEntity();
        taskEntity2.setTaskId(2L);
        taskEntity2.setFunction(functionEntity);
        taskEntity2.setTaskType("Task Type");
        taskEntity2.setStatus("Completed");
        taskEntity2.setStartTime(taskDTO.getStartTime());
        taskEntity2.setEndTime(taskDTO.getEndTime());
    }

    @AfterEach
    void tearDown() {
        taskService.delete(1L);
        Mockito.reset(functionRepository, taskRepository);
    }

    @Test
    void testCreate() {
        when(functionRepository.findById(1L)).thenReturn(Optional.of(functionEntity));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity1);

        TaskDTO createdTask = taskService.create(taskDTO);

        assertNotNull(createdTask);
        assertEquals(taskDTO.getTaskId(), createdTask.getTaskId());
        verify(taskRepository).save(any(TaskEntity.class));
    }

    @Test
    void testRead() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity1));

        TaskDTO foundTask = taskService.read(1L);

        assertNotNull(foundTask);
        assertEquals(taskDTO.getTaskId(), foundTask.getTaskId());
        verify(taskRepository).findById(1L);
    }

    @Test
    void testReadNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskDTO foundTask = taskService.read(1L);

        assertNull(foundTask);
        verify(taskRepository).findById(1L);
    }

    @Test
    void testUpdate() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity1));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity1);

        TaskDTO updatedTask = taskService.update(taskDTO);

        assertNotNull(updatedTask);
        assertEquals(taskDTO.getTaskId(), updatedTask.getTaskId());
        verify(taskRepository).save(any(TaskEntity.class));
    }

    @Test
    void testDelete() {
        taskService.delete(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void testGetByFunction() {
        List<TaskEntity> taskList = new ArrayList<>();
        taskList.add(taskEntity1);

        when(functionRepository.findById(1L)).thenReturn(Optional.of(functionEntity));
        when(taskRepository.findByFunction(functionEntity)).thenReturn(taskList);

        List<TaskDTO> tasks = taskService.getByFunction(1L);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(taskDTO.getTaskId(), tasks.get(0).getTaskId());
        verify(taskRepository).findByFunction(functionEntity);
    }

    @Test
    void testGetByFunctionFunctionNotFound() {
        when(functionRepository.findById(1L)).thenReturn(Optional.empty());

        List<TaskDTO> tasks = taskService.getByFunction(1L);

        assertNull(tasks);
        verify(taskRepository, never()).findByFunction(any());
    }

    @Test
    public void testDepthFirstSearch() {
        List<TaskEntity> tasks = Arrays.asList(taskEntity1, taskEntity2);
        when(taskRepository.findByFunction(functionEntity)).thenReturn(tasks);

        List<TaskDTO> result = taskService.depthFirstSearch(functionEntity);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getTaskId().equals(taskEntity1.getTaskId())));
        assertTrue(result.stream().anyMatch(dto -> dto.getTaskId().equals(taskEntity2.getTaskId())));
    }

    @Test
    public void testBreadthFirstSearch() {
        List<TaskEntity> tasks = Arrays.asList(taskEntity1, taskEntity2);
        when(taskRepository.findByFunction(functionEntity)).thenReturn(tasks);

        List<TaskDTO> result = taskService.breadthFirstSearch(functionEntity);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getTaskId().equals(taskEntity1.getTaskId())));
        assertTrue(result.stream().anyMatch(dto -> dto.getTaskId().equals(taskEntity2.getTaskId())));
    }

    @Test
    public void testFindByStatus() {
        List<TaskEntity> tasks = Arrays.asList(taskEntity1, taskEntity2);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> result = taskService.findByStatus("Pending");

        assertEquals(1, result.size());
        assertEquals(taskEntity1.getTaskId(), result.get(0).getTaskId());
    }

    @Test
    public void testFindByTaskType() {
        List<TaskEntity> tasks = Arrays.asList(taskEntity1, taskEntity2);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> result = taskService.findByTaskType("Task Type");

        assertEquals(1, result.size());
        assertEquals(taskEntity2.getTaskId(), result.get(0).getTaskId());
    }

    @Test
    public void testSortTasksByStartTimeAscending() {
        List<TaskDTO> taskDTOs = Arrays.asList(taskService.toDTO(taskEntity2), taskService.toDTO(taskEntity1)); // Reversed order
        List<TaskDTO> sortedTasks = taskService.sortTasks(taskDTOs, "startTime", true);

        assertEquals(taskEntity2.getTaskId(), sortedTasks.get(0).getTaskId());
        assertEquals(taskEntity1.getTaskId(), sortedTasks.get(1).getTaskId());
    }
}
