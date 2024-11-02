package mathapp.service;

import mathapp.DTO.FunctionPointDTO;
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

    private FunctionPointDTO pointDTO;
    private FunctionPointEntity pointEntity1;
    private FunctionPointEntity pointEntity2;
    private FunctionEntity functionEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pointDTO = new FunctionPointDTO();
        pointDTO.setPointId(1L);
        pointDTO.setFunctionId(1L);
        pointDTO.setXValue(10.0);
        pointDTO.setYValue(20.0);

        functionEntity = new FunctionEntity();
        functionEntity.setFunctionId(1L);
        functionEntity.setName("Test Function");

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
    public void testDepthFirstSearch() {
        List<FunctionPointEntity> points = Arrays.asList(pointEntity1, pointEntity2);
        when(functionPointRepository.findByFunction(functionEntity)).thenReturn(points);

        List<FunctionPointDTO> result = functionPointService.depthFirstSearch(functionEntity);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getPointId().equals(pointEntity1.getPointId())));
        assertTrue(result.stream().anyMatch(dto -> dto.getPointId().equals(pointEntity2.getPointId())));
    }

    @Test
    public void testBreadthFirstSearch() {
        List<FunctionPointEntity> points = Arrays.asList(pointEntity1, pointEntity2);
        when(functionPointRepository.findByFunction(functionEntity)).thenReturn(points);

        List<FunctionPointDTO> result = functionPointService.breadthFirstSearch(functionEntity);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getPointId().equals(pointEntity1.getPointId())));
        assertTrue(result.stream().anyMatch(dto -> dto.getPointId().equals(pointEntity2.getPointId())));
    }

    @Test
    public void testSearchByFunctionName() {
        when(functionPointRepository.findByNameContainingIgnoreCase("Function")).thenReturn(Collections.singletonList(functionEntity));
        when(functionPointRepository.findByFunction(functionEntity)).thenReturn(Collections.singletonList(pointEntity1));

        List<FunctionPointDTO> result = functionPointService.searchByFunctionName("Function", true);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(pointEntity1.getPointId(), result.get(0).getPointId());
    }
}
