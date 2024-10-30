package mathapp.service;

import mathapp.DTO.FunctionDTO;
import mathapp.model.FunctionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mathapp.repository.FunctionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FunctionService {
    @Autowired
    private FunctionRepository functionRepository;

    public FunctionDTO create(FunctionDTO functionDTO) {
        FunctionEntity functionEntity = toEntity(functionDTO);
        FunctionEntity createdFunction = functionRepository.save(functionEntity);
        return toDTO(createdFunction);
    }

    public FunctionDTO read(Long id) {
        return functionRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public FunctionDTO update(FunctionDTO functionDTO) {
        FunctionEntity functionEntity = toEntity(functionDTO);
        FunctionEntity updatedFunction = functionRepository.save(functionEntity);
        return toDTO(updatedFunction);
    }

    public void delete(Long id) {
        functionRepository.deleteById(id);
    }

    public List<FunctionDTO> getByName(String name) {
        return functionRepository.findByName(name)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private FunctionEntity toEntity(FunctionDTO dto) {
        FunctionEntity entity = new FunctionEntity();
        entity.setFunctionId(dto.getFunctionId());
        entity.setName(dto.getName());
        entity.setXFrom(dto.getXFrom());
        entity.setXTo(dto.getXTo());
        entity.setCount(dto.getCount());
        return entity;
    }

    private FunctionDTO toDTO(FunctionEntity entity) {
        FunctionDTO dto = new FunctionDTO();
        dto.setFunctionId(entity.getFunctionId());
        dto.setName(entity.getName());
        dto.setXFrom(entity.getXFrom());
        dto.setXTo(entity.getXTo());
        dto.setCount(entity.getCount());
        return dto;
    }

    public List<FunctionEntity> findByName(String name) {
        return functionRepository.findAll().stream().filter(function -> function.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }
}