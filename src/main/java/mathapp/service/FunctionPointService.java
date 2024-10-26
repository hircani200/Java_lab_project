package mathapp.service;

import mathapp.DTO.FunctionPointDTO;
import mathapp.model.FunctionEntity;
import mathapp.model.FunctionPointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mathapp.repository.FunctionPointRepository;
import mathapp.repository.FunctionRepository;

import java.util.List;
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
}