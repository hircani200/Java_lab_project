package mathapp.controller;

import mathapp.DTO.FunctionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mathapp.service.FunctionService;
import java.util.List;

@RestController
@RequestMapping("/functions")
public class FunctionController {

    @Autowired
    private FunctionService functionService;

    @PostMapping
    public ResponseEntity<FunctionDTO> create(@RequestBody FunctionDTO functionDTO) {
        FunctionDTO createdFunction = functionService.create(functionDTO);
        return ResponseEntity.ok(createdFunction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FunctionDTO> read(@PathVariable Long id) {
        FunctionDTO functionDTO = functionService.read(id);
        return functionDTO != null ? ResponseEntity.ok(functionDTO) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FunctionDTO> update(@PathVariable Long id, @RequestBody FunctionDTO functionDTO) {
        functionDTO.setFunctionId(id);
        FunctionDTO updatedFunction = functionService.update(functionDTO);
        return ResponseEntity.ok(updatedFunction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        functionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FunctionDTO>> getByName(@RequestParam String name) {
        List<FunctionDTO> functions = functionService.getByName(name);
        return ResponseEntity.ok(functions);
    }
}