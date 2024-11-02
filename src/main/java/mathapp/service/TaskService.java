package mathapp.service;

import mathapp.DTO.TaskDTO;
import mathapp.model.TaskEntity;
import mathapp.model.FunctionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mathapp.repository.FunctionRepository;
import mathapp.repository.TaskRepository;

import java.util.*;
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

    public List<TaskDTO> depthFirstSearch(FunctionEntity function) {
        Set<Long> visited = new HashSet<>();
        List<TaskDTO> result = new ArrayList<>();
        dfsHelper(function, visited, result);
        return result;
    }

    private void dfsHelper(FunctionEntity function, Set<Long> visited, List<TaskDTO> result) {
        List<TaskEntity> tasks = taskRepository.findByFunction(function);
        for (TaskEntity task : tasks) {
            if (!visited.contains(task.getTaskId())) {
                visited.add(task.getTaskId());
                result.add(toDTO(task));
                if (task.getFunction() != null) {
                    dfsHelper(task.getFunction(), visited, result);
                }
            }
        }
    }

    public List<TaskDTO> breadthFirstSearch(FunctionEntity function) {
        Set<Long> visited = new HashSet<>();
        List<TaskDTO> result = new ArrayList<>();

        List<TaskEntity> tasks = taskRepository.findByFunction(function);
        Queue<TaskEntity> queue = new LinkedList<>(tasks);

        while (!queue.isEmpty()) {
            TaskEntity current = queue.poll();
            if (!visited.contains(current.getTaskId())) {
                visited.add(current.getTaskId());
                result.add(toDTO(current));
                if (current.getFunction() != null) {
                    List<TaskEntity> relatedTasks = taskRepository.findByFunction(current.getFunction());
                    for (TaskEntity relatedTask : relatedTasks) {
                        if (!visited.contains(relatedTask.getTaskId())) {
                            queue.add(relatedTask);
                        }
                    }
                }
            }
        }
        return result;
    }

    public List<TaskDTO> findByStatus(String status) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getStatus().equalsIgnoreCase(status))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> findByTaskType(String taskType) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getTaskType().equalsIgnoreCase(taskType))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> sortTasks(List<TaskDTO> tasks, String sortBy, boolean ascending) {
        Comparator<TaskDTO> comparator = switch (sortBy.toLowerCase()) {
            case "starttime" -> Comparator.comparing(TaskDTO::getStartTime);
            case "endtime" -> Comparator.comparing(TaskDTO::getEndTime);
            case "status" -> Comparator.comparing(TaskDTO::getStatus);
            case "tasktype" -> Comparator.comparing(TaskDTO::getTaskType);
            default -> throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        };

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return tasks.stream()
                .sorted(comparator)
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

    public TaskDTO toDTO(TaskEntity entity) {
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