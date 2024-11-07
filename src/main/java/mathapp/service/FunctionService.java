package mathapp.service;

import mathapp.dto.FunctionDTO;
import mathapp.model.FunctionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mathapp.repository.FunctionRepository;

import java.util.*;
import java.util.function.Function;
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

    public List<FunctionDTO> getByType(String type) {
        return functionRepository.findByType(type)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private FunctionEntity toEntity(FunctionDTO dto) {
        FunctionEntity entity = new FunctionEntity();
        entity.setFunctionId(dto.getFunctionId());
        entity.setType(FunctionDTO.getType());
        entity.setXFrom(dto.getXFrom());
        entity.setXTo(dto.getXTo());
        entity.setCount(dto.getCount());
        return entity;
    }

    private FunctionDTO toDTO(FunctionEntity entity) {
        FunctionDTO dto = new FunctionDTO();
        dto.setFunctionId(entity.getFunctionId());
        dto.setType(entity.getType());
        dto.setXFrom(entity.getXFrom());
        dto.setXTo(entity.getXTo());
        dto.setCount(entity.getCount());
        return dto;
    }

    public List<FunctionDTO> searchByType(String type, boolean sortAscending) {
        List<FunctionEntity> functions = functionRepository.findByTypeContainingIgnoreCase(type);
        Comparator<FunctionEntity> comparator = Comparator.comparing(FunctionEntity::getType);
        if (!sortAscending) {
            comparator = comparator.reversed();
        }
        return functions.stream()
                .sorted(comparator)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<FunctionDTO> searchByXRange(Double xFrom, Double xTo, boolean sortAscending) {
        List<FunctionEntity> functions = functionRepository.findAll().stream()
                .filter(function -> function.getXFrom() >= xFrom && function.getXTo() <= xTo)
                .toList();

        Comparator<FunctionEntity> comparator = Comparator.comparing(FunctionEntity::getXFrom);
        if (!sortAscending) {
            comparator = comparator.reversed();
        }
        return functions.stream()
                .sorted(comparator)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<FunctionDTO> sortAndMap(List<FunctionEntity> functions, Function<FunctionEntity, Comparable> keyExtractor, boolean sortAscending) {
        Comparator<FunctionEntity> comparator = Comparator.comparing(keyExtractor);
        if (!sortAscending) {
            comparator = comparator.reversed();
        }
        return functions.stream()
                .sorted(comparator)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<FunctionEntity> findByType(String type) {
        return functionRepository.findAll().stream().filter(function -> function.getType().equalsIgnoreCase(type)).collect(Collectors.toList());
    }
}