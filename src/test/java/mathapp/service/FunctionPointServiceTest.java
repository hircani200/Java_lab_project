package mathapp.service;

import mathapp.dto.FunctionPointDTO;
import mathapp.model.FunctionEntity;
import mathapp.model.FunctionPointEntity;
import mathapp.repository.FunctionPointRepository;
import mathapp.repository.FunctionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FunctionPointServiceTest {

    @InjectMocks
    private FunctionPointService functionPointService;

    @Mock
    private FunctionPointRepository functionPointRepository;

    @Mock
    private FunctionRepository functionRepository;

    @Mock
    private FunctionEntity functionEntity;

    private FunctionPointDTO pointDTO;
    private FunctionPointEntity pointEntity1;
    private FunctionPointEntity pointEntity2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pointDTO = new FunctionPointDTO();
        pointDTO.setPointId(1L);
        pointDTO.setFunctionId(1L);
        pointDTO.setXValue(10.0);
        pointDTO.setYValue(20.0);

        pointEntity1 = new FunctionPointEntity();
        pointEntity1.setPointId(1L);
        pointEntity1.setFunction(functionEntity);
        pointEntity1.setXValue(10.0);
        pointEntity1.setYValue(20.0);

        pointEntity2 = new FunctionPointEntity();
        pointEntity2.setPointId(2L);
        pointEntity2.setFunction(functionEntity);
        pointEntity2.setXValue(0.0);
        pointEntity2.setYValue(2.1);

        when(functionEntity.getFunctionId()).thenReturn(1L);
        when(functionEntity.getType()).thenReturn("SampleFunction");
    }

    @AfterEach
    void tearDown() {
        functionPointService.delete(1L);
        Mockito.reset(functionPointRepository, functionRepository);
    }

    @Test
    void testCreate() {
        when(functionPointRepository.save(any())).thenReturn(pointEntity1);
        when(functionRepository.findById(pointDTO.getFunctionId())).thenReturn(Optional.of(functionEntity));

        FunctionPointDTO createdPoint = functionPointService.create(pointDTO);

        assertEquals(pointDTO.getXValue(), createdPoint.getXValue());
        assertEquals(pointDTO.getYValue(), createdPoint.getYValue());
        verify(functionPointRepository).save(any());
    }

    @Test
    void testRead() {
        Long pointId = 1L;
        when(functionPointRepository.findById(pointId)).thenReturn(Optional.of(pointEntity1));

        FunctionPointDTO foundPoint = functionPointService.read(pointId);

        assertEquals(pointDTO.getPointId(), foundPoint.getPointId());
        verify(functionPointRepository).findById(pointId);
    }

    @Test
    void testReadNotFound() {
        Long pointId = 2L;
        when(functionPointRepository.findById(pointId)).thenReturn(Optional.empty());

        FunctionPointDTO foundPoint = functionPointService.read(pointId);

        assertNull(foundPoint);
        verify(functionPointRepository).findById(pointId);
    }

    @Test
    void testUpdate() {
        when(functionPointRepository.save(any())).thenReturn(pointEntity1);
        when(functionRepository.findById(pointDTO.getFunctionId())).thenReturn(Optional.of(functionEntity));

        FunctionPointDTO updatedPoint = functionPointService.update(pointDTO);

        assertEquals(pointDTO.getPointId(), updatedPoint.getPointId());
        verify(functionPointRepository).save(any());
    }

    @Test
    void testDelete() {
        Long pointId = 1L;
        functionPointService.delete(pointId);
        verify(functionPointRepository).deleteById(pointId);
    }

    @Test
    void testGetByFunction() {
        Long functionId = 1L;
        when(functionRepository.findById(functionId)).thenReturn(Optional.of(functionEntity));
        when(functionPointRepository.findByFunction(functionEntity)).thenReturn(Arrays.asList(pointEntity1));

        List<FunctionPointDTO> points = functionPointService.getByFunction(functionId);

        assertEquals(1, points.size());
        assertEquals(pointDTO.getPointId(), points.get(0).getPointId());
        verify(functionPointRepository).findByFunction(functionEntity);
    }

    @Test
    void testGetByFunctionNotFound() {
        Long functionId = 2L;
        when(functionRepository.findById(functionId)).thenReturn(Optional.empty());

        List<FunctionPointDTO> points = functionPointService.getByFunction(functionId);

        assertNull(points);
        verify(functionPointRepository, never()).findByFunction(any());
    }

    @Test
    public void testSearchByFunctionName() {
        when(functionRepository.findAll()).thenReturn(Collections.singletonList(functionEntity));
        when(functionPointRepository.findByFunction(functionEntity)).thenReturn(Collections.singletonList(pointEntity1));

        List<FunctionPointDTO> result = functionPointService.searchByFunctionType("Sample", true);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(pointEntity1.getPointId(), result.get(0).getPointId());
    }
}
