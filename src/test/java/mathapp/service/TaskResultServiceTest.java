package mathapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import mathapp.DTO.TaskResultDTO;
import mathapp.model.TaskEntity;
import mathapp.model.TaskResultEntity;
import mathapp.repository.TaskRepository;
import mathapp.repository.TaskResultRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TaskResultServiceTest {

    @InjectMocks
    private TaskResultService taskResultService;

    @Mock
    private TaskResultRepository taskResultRepository;

    @Mock
    private TaskRepository taskRepository;

    private TaskResultDTO taskResultDTO;
    private TaskResultEntity taskResultEntity;
    private TaskEntity taskEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        taskEntity = new TaskEntity();
        taskEntity.setTaskId(1L);

        taskResultDTO = new TaskResultDTO();
        taskResultDTO.setResultId(1L);
        taskResultDTO.setTaskId(1L);
        taskResultDTO.setResult(100.0);

        taskResultEntity = new TaskResultEntity();
        taskResultEntity.setResultId(1L);
        taskResultEntity.setTask(taskEntity);
        taskResultEntity.setResult(100.0);
    }

    @AfterEach
    void tearDown() {
        taskResultService.delete(1L);
        Mockito.reset(taskResultRepository, taskRepository);
    }

    @Test
    void testCreate() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskResultRepository.save(any(TaskResultEntity.class))).thenReturn(taskResultEntity);

        TaskResultDTO createdResult = taskResultService.create(taskResultDTO);

        assertNotNull(createdResult);
        assertEquals(taskResultDTO.getResultId(), createdResult.getResultId());
        verify(taskResultRepository).save(any(TaskResultEntity.class));
    }

    @Test
    void testRead() {
        when(taskResultRepository.findById(1L)).thenReturn(Optional.of(taskResultEntity));

        TaskResultDTO foundResult = taskResultService.read(1L);

        assertNotNull(foundResult);
        assertEquals(taskResultDTO.getResultId(), foundResult.getResultId());
        verify(taskResultRepository).findById(1L);
    }

    @Test
    void testReadNotFound() {
        when(taskResultRepository.findById(1L)).thenReturn(Optional.empty());

        TaskResultDTO foundResult = taskResultService.read(1L);

        assertNull(foundResult);
        verify(taskResultRepository).findById(1L);
    }

    @Test
    void testUpdate() {
        when(taskResultRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskResultRepository.save(any(TaskResultEntity.class))).thenReturn(taskResultEntity);

        TaskResultDTO updatedResult = taskResultService.update(taskResultDTO);

        assertNotNull(updatedResult);
        assertEquals(taskResultDTO.getResultId(), updatedResult.getResultId());
        verify(taskResultRepository).save(any(TaskResultEntity.class));
    }

    @Test
    void testUpdateNotFound() {
        when(taskResultRepository.existsById(1L)).thenReturn(false);

        TaskResultDTO updatedResult = taskResultService.update(taskResultDTO);

        assertNull(updatedResult);
        verify(taskResultRepository, never()).save(any());
    }

    @Test
    void testDelete() {
        when(taskResultRepository.existsById(1L)).thenReturn(true);
        taskResultService.delete(1L);
        verify(taskResultRepository).deleteById(1L);
    }

    @Test
    public void testGetByTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskResultRepository.findByTask(taskEntity)).thenReturn(taskResultEntity);

        TaskResultDTO resultDTO = taskResultService.getByTask(1L);

        assertNotNull(resultDTO);
        assertEquals(taskResultEntity.getResultId(), resultDTO.getResultId());
    }

    @Test
    void testGetByTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskResultDTO result = taskResultService.getByTask(1L);

        assertNull(result);
        verify(taskResultRepository, never()).findByTask(any());
    }

    @Test
    public void testSearchByResult() {
        List<TaskResultEntity> results = Arrays.asList(taskResultEntity);
        when(taskResultRepository.findAll()).thenReturn(results);

        List<TaskResultDTO> foundResults = taskResultService.searchByResult(100.0, true);

        assertEquals(1, foundResults.size());
        assertEquals(taskResultEntity.getResultId(), foundResults.get(0).getResultId());
    }

    @Test
    public void testSearchByTask() {
        List<TaskResultEntity> results = Arrays.asList(taskResultEntity);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskResultRepository.findAll()).thenReturn(results);

        List<TaskResultDTO> foundResults = taskResultService.searchByTask(1L);

        assertEquals(1, foundResults.size());
        assertEquals(taskResultEntity.getResultId(), foundResults.get(0).getResultId());
    }
}
