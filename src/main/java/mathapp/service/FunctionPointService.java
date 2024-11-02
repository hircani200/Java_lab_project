package mathapp.service;

import mathapp.DTO.FunctionPointDTO;
import mathapp.model.FunctionEntity;
import mathapp.model.FunctionPointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mathapp.repository.FunctionPointRepository;
import mathapp.repository.FunctionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FunctionPointService {
    @Autowired
    private FunctionPointRepository functionPointRepository;
    @Autowired
    private FunctionRepository functionRepository;

    public FunctionPointDTO create(FunctionPointDTO pointDTO) {
        FunctionPointEntity pointEntity = toEntity(pointDTO);
        FunctionPointEntity createdPoint = functionPointRepository.save(pointEntity);
        return toDTO(createdPoint);
    }

    public FunctionPointDTO read(Long id) {
        return functionPointRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public FunctionPointDTO update(FunctionPointDTO pointDTO) {
        FunctionPointEntity pointEntity = toEntity(pointDTO);
        FunctionPointEntity updatedPoint = functionPointRepository.save(pointEntity);
        return toDTO(updatedPoint);
    }

    public void delete(Long id) {
        functionPointRepository.deleteById(id);
    }

    public List<FunctionPointDTO> getByFunction(Long functionId) {
        FunctionEntity function = functionRepository.findById(functionId).orElse(null);
        if (function == null) {
            return null;
        }
        return functionPointRepository.findByFunction(function)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private FunctionPointEntity toEntity(FunctionPointDTO dto) {
        FunctionPointEntity entity = new FunctionPointEntity();
        entity.setPointId(dto.getPointId());
        entity.setFunction(functionRepository.findById(dto.getFunctionId()).orElse(null));
        entity.setXValue(dto.getXValue());
        entity.setYValue(dto.getYValue());
        return entity;
    }

    private FunctionPointDTO toDTO(FunctionPointEntity entity) {
        FunctionPointDTO dto = new FunctionPointDTO();
        dto.setPointId(entity.getPointId());
        dto.setFunctionId(entity.getFunction().getFunctionId());
        dto.setXValue(entity.getXValue());
        dto.setYValue(entity.getYValue());
        return dto;
    }

    public List<FunctionPointDTO> searchByFunctionName(String functionName, boolean sortAscending) {
        List<FunctionEntity> functions = functionPointRepository.findByNameContainingIgnoreCase(functionName);
        List<FunctionPointEntity> points = functions.stream()
                .flatMap(function -> functionPointRepository.findByFunction(function).stream())
                .collect(Collectors.toList());

        return sortAndMap(points, sortAscending);
    }

    public List<FunctionPointDTO> depthFirstSearch(FunctionEntity function) {
        Set<Long> visited = new HashSet<>();
        List<FunctionPointDTO> result = new ArrayList<>();
        dfsHelper(function, visited, result);
        return result;
    }

    private void dfsHelper(FunctionEntity function, Set<Long> visited, List<FunctionPointDTO> result) {
        List<FunctionPointEntity> points = functionPointRepository.findByFunction(function);
        for (FunctionPointEntity point : points) {
            if (!visited.contains(point.getPointId())) {
                visited.add(point.getPointId());
                result.add(toDTO(point));

                if (point.getFunction() != null) {
                    dfsHelper(point.getFunction(), visited, result);
                }
            }
        }
    }

    public List<FunctionPointDTO> breadthFirstSearch(FunctionEntity function) {
        Set<Long> visited = new HashSet<>();
        List<FunctionPointDTO> result = new ArrayList<>();

        List<FunctionPointEntity> points = functionPointRepository.findByFunction(function);
        Queue<FunctionPointEntity> queue = new LinkedList<>(points);

        while (!queue.isEmpty()) {
            FunctionPointEntity current = queue.poll();
            if (!visited.contains(current.getPointId())) {
                visited.add(current.getPointId());
                result.add(toDTO(current));

                if (current.getFunction() != null) {
                    List<FunctionPointEntity> relatedPoints = functionPointRepository.findByFunction(current.getFunction());
                    for (FunctionPointEntity relatedPoint : relatedPoints) {
                        if (!visited.contains(relatedPoint.getPointId())) {
                            queue.add(relatedPoint);
                        }
                    }
                }
            }
        }
        return result;
    }


    private List<FunctionPointDTO> sortAndMap(List<FunctionPointEntity> points, boolean sortAscending) {
        Comparator<FunctionPointEntity> comparator = Comparator.comparing(FunctionPointEntity::getXValue);
        if (!sortAscending) {
            comparator = comparator.reversed();
        }
        return points.stream()
                .sorted(comparator)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}