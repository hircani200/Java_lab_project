package mathapp.controller;

import mathapp.DTO.TaskResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mathapp.service.TaskResultService;

@RestController
@RequestMapping("/task_results")
public class TaskResultController {

    @Autowired
    private TaskResultService taskResultService;

    @PostMapping
    public ResponseEntity<TaskResultDTO> create(@RequestBody TaskResultDTO taskResultDTO) {
        TaskResultDTO createdResult = taskResultService.create(taskResultDTO);
        return ResponseEntity.ok(createdResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResultDTO> read(@PathVariable Long id) {
        TaskResultDTO taskResultDTO = taskResultService.read(id);
        return taskResultDTO != null ? ResponseEntity.ok(taskResultDTO) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResultDTO> update(@PathVariable Long id, @RequestBody TaskResultDTO taskResultDTO) {
        taskResultDTO.setTaskId(id);
        TaskResultDTO updatedResult = taskResultService.update(taskResultDTO);
        return ResponseEntity.ok(updatedResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskResultService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<TaskResultDTO> getByTask(@PathVariable Long taskId) {
        TaskResultDTO resultDTO = taskResultService.getByTask(taskId);
        return resultDTO != null ? ResponseEntity.ok(resultDTO) : ResponseEntity.notFound().build();
    }
}