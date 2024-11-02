package mathapp.service;

import mathapp.DTO.FunctionDTO;
import mathapp.model.FunctionEntity;
import mathapp.model.FunctionPointEntity;
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

    public List<FunctionDTO> searchByName(String name, boolean sortAscending) {
        List<FunctionEntity> functions = functionRepository.findByNameContainingIgnoreCase(name);
        Comparator<FunctionEntity> comparator = Comparator.comparing(FunctionEntity::getName);
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

    public List<FunctionEntity> depthFirstSearch(FunctionEntity root, String targetName) {
        List<FunctionEntity> result = new ArrayList<>();
        if (root.getName().equalsIgnoreCase(targetName)) {
            result.add(root);
        }
        for (FunctionPointEntity point : root.getPoints()) {
            result.addAll(depthFirstSearch(point.getFunction(), targetName));
        }
        return result;
    }

    public List<FunctionEntity> breadthFirstSearch(FunctionEntity root, String targetName) {
        List<FunctionEntity> result = new ArrayList<>();
        Queue<FunctionEntity> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            FunctionEntity current = queue.poll();
            if (current.getName().equalsIgnoreCase(targetName)) {
                result.add(current);
            }
            queue.addAll(current.getPoints().stream().map(FunctionPointEntity::getFunction).collect(Collectors.toList()));
        }
        return result;
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

    public List<FunctionEntity> findByName(String name) {
        return functionRepository.findAll().stream().filter(function -> function.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }
}