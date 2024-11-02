package mathapp.service;

import mathapp.DTO.FunctionDTO;
import mathapp.model.FunctionEntity;
import mathapp.repository.FunctionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FunctionServiceTest {

    @InjectMocks
    private FunctionService functionService;

    @Mock
    private FunctionRepository functionRepository;

    private FunctionDTO functionDTO;
    private FunctionEntity functionEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        functionDTO = new FunctionDTO();
        functionDTO.setFunctionId(1L);
        functionDTO.setName("Test Function");
        functionDTO.setXFrom(0.0);
        functionDTO.setXTo(10.0);
        functionDTO.setCount(5);

        functionEntity = new FunctionEntity();
        functionEntity.setFunctionId(1L);
        functionEntity.setName("Test Function");
        functionEntity.setXFrom(0.0);
        functionEntity.setXTo(10.0);
        functionEntity.setCount(5);
        functionEntity.setPoints(new ArrayList<>());
    }

    @AfterEach
    void tearDown() {
        functionService.delete(1L);
        Mockito.reset(functionRepository);
    }

    @Test
    void testCreate() {
        when(functionRepository.save(any(FunctionEntity.class))).thenReturn(functionEntity);

        FunctionDTO createdFunction = functionService.create(functionDTO);

        assertEquals(FunctionDTO.getName(), FunctionDTO.getName());
        verify(functionRepository).save(any(FunctionEntity.class));
    }

    @Test
    public void testRead() {
        when(functionRepository.findById(functionEntity.getFunctionId())).thenReturn(Optional.of(functionEntity));

        FunctionDTO foundFunction = functionService.read(functionEntity.getFunctionId());

        assertNotNull(foundFunction);
        assertEquals(functionEntity.getName(), foundFunction.getName());
    }

    @Test
    void testReadNotFound() {
        Long functionId = 2L;
        when(functionRepository.findById(functionId)).thenReturn(Optional.empty());

        FunctionDTO foundFunction = functionService.read(functionId);

        assertNull(foundFunction);
        verify(functionRepository).findById(functionId);
    }

    @Test
    void testUpdate() {
        when(functionRepository.save(any(FunctionEntity.class))).thenReturn(functionEntity);

        FunctionDTO updatedFunction = functionService.update(functionDTO);

        assertEquals(FunctionDTO.getName(), FunctionDTO.getName());
        verify(functionRepository).save(any(FunctionEntity.class));
    }

    @Test
    void testDelete() {
        Long functionId = 1L;
        functionService.delete(functionId);
        verify(functionRepository).deleteById(functionId);
    }

    @Test
    void testSearchByName() {
        when(functionRepository.findByNameContainingIgnoreCase("Test")).thenReturn(Collections.singletonList(functionEntity));

        List<FunctionDTO> functionDTOs = functionService.searchByName("Test", true);

        assertNotNull(functionDTOs);
        assertEquals(1, functionDTOs.size());
        assertEquals("Test Function", FunctionDTO.getName());
    }

    @Test
    public void testSearchByNameNotFound() {
        when(functionRepository.findByNameContainingIgnoreCase("Nonexistent")).thenReturn(Collections.emptyList());

        List<FunctionDTO> results = functionService.searchByName("Nonexistent", true);

        assertTrue(results.isEmpty());
    }

    @Test
    void testSearchByXRange() {
        when(functionRepository.findByXFromBetween(0.0, 10.0)).thenReturn(Arrays.asList(functionEntity));

        List<FunctionDTO> functionDTO = functionService.searchByXRange(0.0, 10.0, true);

        assertNotNull(functionDTO);
        assertEquals("Test Function", FunctionDTO.getName());
    }

    @Test
    public void testDepthFirstSearch() {
        List<FunctionEntity> result = functionService.depthFirstSearch(functionEntity, "Test Function");

        assertEquals(1, result.size());
        assertEquals("Test Function", result.get(0).getName());
    }

    @Test
    public void testDepthFirstSearchNotFound() {
        List<FunctionEntity> result = functionService.depthFirstSearch(functionEntity, "Function");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testBreadthFirstSearch() {
        List<FunctionEntity> result = functionService.breadthFirstSearch(functionEntity, "Test Function");

        assertEquals(1, result.size());
        assertEquals("Test Function", result.get(0).getName());
    }

    @Test
    public void testBreadthFirstSearchNotFound() {
        List<FunctionEntity> result = functionService.breadthFirstSearch(functionEntity, "Function");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testSortAndMapAscending() {
        List<FunctionEntity> functions = new ArrayList<>();

        FunctionEntity functionEntity1 = new FunctionEntity();
        functionEntity1.setFunctionId(1L);
        functionEntity1.setName("Function");
        functionEntity1.setXFrom(0.0);
        functionEntity1.setXTo(10.0);
        functionEntity1.setCount(5);

        functions.add(0, functionEntity1);
        functions.add(1, functionEntity);

        List<FunctionDTO> sorted = functionService.sortAndMap(functions, FunctionEntity::getName, true);

        assertEquals(2, sorted.size());
        assertEquals("Test Function", sorted.get(0).getName());
        assertEquals("Test Function", sorted.get(1).getName());
    }

    @Test
    public void testSortAndMapDescending() {
        List<FunctionEntity> functions = new ArrayList<>();

        FunctionEntity functionEntity1 = new FunctionEntity();
        functionEntity1.setFunctionId(1L);
        functionEntity1.setName("Function");
        functionEntity1.setXFrom(0.0);
        functionEntity1.setXTo(10.0);
        functionEntity1.setCount(5);

        functions.add(0, functionEntity1);
        functions.add(1, functionEntity);

        List<FunctionDTO> sorted = functionService.sortAndMap(functions, FunctionEntity::getName, false);

        assertEquals(2, sorted.size());
        assertEquals("Function", sorted.get(0).getName());
        assertEquals("Function", sorted.get(1).getName());
    }


    @Test
    public void testFindByName() {
        when(functionRepository.findAll()).thenReturn(List.of(functionEntity));

        List<FunctionEntity> results = functionService.findByName(functionEntity.getName());

        assertEquals(1, results.size());
        assertEquals(functionEntity.getName(), results.get(0).getName());
    }

    @Test
    public void testFindByNameNotFound() {
        when(functionRepository.findAll()).thenReturn(new ArrayList<>());

        List<FunctionEntity> results = functionService.findByName("Nonexistent Function");

        assertTrue(results.isEmpty());
    }

}