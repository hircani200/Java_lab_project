package mathapp.service;

import mathapp.dto.FunctionDTO;
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
        functionDTO.setType("Test Function");
        functionDTO.setXFrom(0.0);
        functionDTO.setXTo(10.0);
        functionDTO.setCount(5);

        functionEntity = new FunctionEntity();
        functionEntity.setFunctionId(1L);
        functionEntity.setType("Test Function");
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

        assertEquals(FunctionDTO.getType(), FunctionDTO.getType());
        verify(functionRepository).save(any(FunctionEntity.class));
    }

    @Test
    public void testRead() {
        when(functionRepository.findById(functionEntity.getFunctionId())).thenReturn(Optional.of(functionEntity));

        FunctionDTO foundFunction = functionService.read(functionEntity.getFunctionId());

        assertNotNull(foundFunction);
        assertEquals(functionEntity.getType(), foundFunction.getType());
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

        assertEquals(FunctionDTO.getType(), FunctionDTO.getType());
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
        when(functionRepository.findByTypeContainingIgnoreCase("Test")).thenReturn(Collections.singletonList(functionEntity));

        List<FunctionDTO> functionDTOs = functionService.searchByType("Test", true);

        assertNotNull(functionDTOs);
        assertEquals(1, functionDTOs.size());
        assertEquals("Test Function", FunctionDTO.getType());
    }

    @Test
    public void testSearchByNameNotFound() {
        when(functionRepository.findByTypeContainingIgnoreCase("Nonexistent")).thenReturn(Collections.emptyList());

        List<FunctionDTO> results = functionService.searchByType("Nonexistent", true);

        assertTrue(results.isEmpty());
    }

    @Test
    public void testSortAndMapAscending() {
        List<FunctionEntity> functions = new ArrayList<>();

        FunctionEntity functionEntity1 = new FunctionEntity();
        functionEntity1.setFunctionId(1L);
        functionEntity1.setType("Function");
        functionEntity1.setXFrom(0.0);
        functionEntity1.setXTo(10.0);
        functionEntity1.setCount(5);

        functions.add(0, functionEntity1);
        functions.add(1, functionEntity);

        List<FunctionDTO> sorted = functionService.sortAndMap(functions, FunctionEntity::getType, true);

        assertEquals(2, sorted.size());
        assertEquals("Test Function", sorted.get(0).getType());
        assertEquals("Test Function", sorted.get(1).getType());
    }

    @Test
    public void testSortAndMapDescending() {
        List<FunctionEntity> functions = new ArrayList<>();

        FunctionEntity functionEntity1 = new FunctionEntity();
        functionEntity1.setFunctionId(1L);
        functionEntity1.setType("Function");
        functionEntity1.setXFrom(0.0);
        functionEntity1.setXTo(10.0);
        functionEntity1.setCount(5);

        functions.add(0, functionEntity1);
        functions.add(1, functionEntity);

        List<FunctionDTO> sorted = functionService.sortAndMap(functions, FunctionEntity::getType, false);

        assertEquals(2, sorted.size());
        assertEquals("Function", sorted.get(0).getType());
        assertEquals("Function", sorted.get(1).getType());
    }


    @Test
    public void testFindByType() {
        when(functionRepository.findAll()).thenReturn(List.of(functionEntity));

        List<FunctionEntity> results = functionService.findByType(functionEntity.getType());

        assertEquals(1, results.size());
        assertEquals(functionEntity.getType(), results.get(0).getType());
    }

    @Test
    public void testFindByTypeNotFound() {
        when(functionRepository.findAll()).thenReturn(new ArrayList<>());

        List<FunctionEntity> results = functionService.findByType("Nonexistent Function");

        assertTrue(results.isEmpty());
    }

}