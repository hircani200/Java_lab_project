package mathapp.controller;

import mathapp.dto.FunctionPointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mathapp.service.FunctionPointService;
import java.util.List;

@RestController
@RequestMapping("/function_points")
public class FunctionPointController {

    @Autowired
    private FunctionPointService functionPointService;

    @PostMapping
    public ResponseEntity<FunctionPointDTO> create(@RequestBody FunctionPointDTO functionPointDTO) {
        FunctionPointDTO createdPoint = functionPointService.create(functionPointDTO);
        return ResponseEntity.ok(createdPoint);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FunctionPointDTO> read(@PathVariable Long id) {
        FunctionPointDTO functionPointDTO = functionPointService.read(id);
        return functionPointDTO != null ? ResponseEntity.ok(functionPointDTO) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FunctionPointDTO> update(@PathVariable Long id, @RequestBody FunctionPointDTO functionPointDTO) {
        functionPointDTO.setPointId(id);
        FunctionPointDTO updatedPoint = functionPointService.update(functionPointDTO);
        return ResponseEntity.ok(updatedPoint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        functionPointService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/function/{functionId}")
    public ResponseEntity<List<FunctionPointDTO>> getByFunction(@PathVariable Long functionId) {
        List<FunctionPointDTO> points = functionPointService.getByFunction(functionId);
        return points != null ? ResponseEntity.ok(points) : ResponseEntity.notFound().build();
    }
}