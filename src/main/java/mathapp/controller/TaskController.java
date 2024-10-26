package mathapp.controller;

import mathapp.DTO.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mathapp.service.TaskService;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.create(taskDTO);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> read(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.read(id);
        return taskDTO != null ? ResponseEntity.ok(taskDTO) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        taskDTO.setTaskId(id);
        TaskDTO updatedTask = taskService.update(taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/function/{functionId}")
    public ResponseEntity<List<TaskDTO>> getByFunction(@PathVariable Long functionId) {
        List<TaskDTO> tasks = taskService.getByFunction(functionId);
        return tasks != null ? ResponseEntity.ok(tasks) : ResponseEntity.notFound().build();
    }
}