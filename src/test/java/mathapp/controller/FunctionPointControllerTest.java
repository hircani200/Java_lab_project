package mathapp.controller;

import mathapp.dto.FunctionPointDTO;
import mathapp.service.FunctionPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FunctionPointControllerTest {

    @Mock
    private FunctionPointService functionPointService;

    @InjectMocks
    private FunctionPointController functionPointController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        FunctionPointDTO functionPointDTO = new FunctionPointDTO();
        functionPointDTO.setXValue(2.0);
        functionPointDTO.setYValue(4.0);

        when(functionPointService.create(any(FunctionPointDTO.class))).thenReturn(functionPointDTO);

        ResponseEntity<FunctionPointDTO> response = functionPointController.create(functionPointDTO);
        assertNotNull(response.getBody());
        assertEquals(2.0, response.getBody().getXValue());
        verify(functionPointService, times(1)).create(any(FunctionPointDTO.class));
    }

    @Test
    public void testRead() {
        Long id = 1L;
        FunctionPointDTO functionPointDTO = new FunctionPointDTO();
        functionPointDTO.setPointId(id);

        when(functionPointService.read(id)).thenReturn(functionPointDTO);

        ResponseEntity<FunctionPointDTO> response = functionPointController.read(id);
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getPointId());
        verify(functionPointService, times(1)).read(id);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        doNothing().when(functionPointService).delete(id);

        ResponseEntity<Void> response = functionPointController.delete(id);
        assertEquals(200, response.getStatusCodeValue());
        verify(functionPointService, times(1)).delete(id);
    }

    @Test
    public void testGetByFunction() {
        Long functionId = 2L;
        List<FunctionPointDTO> points = Collections.singletonList(new FunctionPointDTO());

        when(functionPointService.getByFunction(functionId)).thenReturn(points);

        ResponseEntity<List<FunctionPointDTO>> response = functionPointController.getByFunction(functionId);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        verify(functionPointService, times(1)).getByFunction(functionId);
    }
}