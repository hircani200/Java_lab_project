package mathapp.service;

import mathapp.DTO.TaskResultDTO;
import mathapp.model.TaskEntity;
import mathapp.model.TaskResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mathapp.repository.TaskRepository;
import mathapp.repository.TaskResultRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskResultService {

    @Autowired
    private TaskResultRepository taskResultRepository;

    @Autowired
    private TaskRepository taskRepository;

    public TaskResultDTO create(TaskResultDTO resultDTO) {
        TaskResultEntity resultEntity = toEntity(resultDTO);
        TaskResultEntity createdResult = taskResultRepository.save(resultEntity);
        return toDTO(createdResult);
    }

    public TaskResultDTO read(Long id) {
        return taskResultRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public TaskResultDTO update(TaskResultDTO resultDTO) {
        if (!taskResultRepository.existsById(resultDTO.getResultId())) {
            return null;
        }
        TaskResultEntity resultEntity = toEntity(resultDTO);
        TaskResultEntity updatedResult = taskResultRepository.save(resultEntity);
        return toDTO(updatedResult);
    }

    public void delete(Long id) {
        if (taskResultRepository.existsById(id)) {
            taskResultRepository.deleteById(id);
        }
    }

    public TaskResultDTO getByTask(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return null;
        }
        TaskResultEntity result = taskResultRepository.findByTask(task);
        return toDTO(result);
    }

    private TaskResultEntity toEntity(TaskResultDTO dto) {
        TaskResultEntity entity = new TaskResultEntity();
        entity.setResultId(dto.getResultId());

        TaskEntity task = taskRepository.findById(dto.getTaskId()).orElse(null);
        entity.setTask(task);

        entity.setResult(dto.getResult());
        return entity;
    }

    private TaskResultDTO toDTO(TaskResultEntity entity) {
        if (entity == null) {
            return null;
        }
        TaskResultDTO dto = new TaskResultDTO();
        dto.setResultId(entity.getResultId());
        dto.setTaskId(entity.getTask() != null ? entity.getTask().getTaskId() : null);
        dto.setResult(entity.getResult());
        return dto;
    }

    public List<TaskResultDTO> searchByResult(Double resultValue, boolean sortAscending) {
        List<TaskResultEntity> results = taskResultRepository.findAll().stream()
                .filter(result -> result.getResult().equals(resultValue))
                .collect(Collectors.toList());

        return sortAndMap(results, sortAscending);
    }

    public List<TaskResultDTO> searchByTask(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return Collections.emptyList();
        }

        List<TaskResultEntity> results = taskResultRepository.findAll().stream()
                .filter(result -> result.getTask() != null && result.getTask().getTaskId().equals(taskId))
                .toList();

        return results.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<TaskResultDTO> sortAndMap(List<TaskResultEntity> results, boolean sortAscending) {
        Comparator<TaskResultEntity> comparator = Comparator.comparing(TaskResultEntity::getResult);
        if (!sortAscending) {
            comparator = comparator.reversed();
        }
        return results.stream()
                .sorted(comparator)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}