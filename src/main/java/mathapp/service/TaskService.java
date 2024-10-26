package mathapp.service;

import mathapp.DTO.TaskDTO;
import mathapp.model.TaskEntity;
import mathapp.model.FunctionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mathapp.repository.FunctionRepository;
import mathapp.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private FunctionRepository functionRepository;

    public TaskDTO create(TaskDTO taskDTO) {
        TaskEntity taskEntity = toEntity(taskDTO);
        TaskEntity createdTask = taskRepository.save(taskEntity);
        return toDTO(createdTask);
    }

    public TaskDTO read(Long id) {
        return taskRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public TaskDTO update(TaskDTO taskDTO) {
        TaskEntity taskEntity = toEntity(taskDTO);
        TaskEntity updatedTask = taskRepository.save(taskEntity);
        return toDTO(updatedTask);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskDTO> getByFunction(Long functionId) {
        FunctionEntity function = functionRepository.findById(functionId).orElse(null);
        if (function == null) {
            return null;
        }
        return taskRepository.findByFunction(function)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private TaskEntity toEntity(TaskDTO dto) {
        TaskEntity entity = new TaskEntity();
        entity.setTaskId(dto.getTaskId());
        entity.setFunction(functionRepository.findById(dto.getFunctionId()).orElse(null));
        entity.setTaskType(dto.getTaskType());
        entity.setStatus(dto.getStatus());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        return entity;
    }

    private TaskDTO toDTO(TaskEntity entity) {
        TaskDTO dto = new TaskDTO();
        dto.setTaskId(entity.getTaskId());
        dto.setFunctionId(entity.getFunction().getFunctionId());
        dto.setTaskType(entity.getTaskType());
        dto.setStatus(entity.getStatus());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
}