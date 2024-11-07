package mathapp.controller;

import mathapp.dto.FunctionDTO;
import mathapp.service.FunctionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FunctionControllerTest {

    @Mock
    private FunctionService functionService;

    @InjectMocks
    private FunctionController functionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        FunctionDTO functionDTO = new FunctionDTO();
        functionDTO.setType("Test Function");

        when(functionService.create(any(FunctionDTO.class))).thenReturn(functionDTO);

        ResponseEntity<FunctionDTO> response = functionController.create(functionDTO);
        assertNotNull(response.getBody());
        assertEquals("Test Function", response.getBody().getType());
        verify(functionService, times(1)).create(any(FunctionDTO.class));
    }

    @Test
    public void testRead() {
        Long id = 1L;
        FunctionDTO functionDTO = new FunctionDTO();
        functionDTO.setFunctionId(id);

        when(functionService.read(id)).thenReturn(functionDTO);

        ResponseEntity<FunctionDTO> response = functionController.read(id);
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getFunctionId());
        verify(functionService, times(1)).read(id);
    }

    @Test
    public void testReadNotFound() {
        Long id = 1L;

        when(functionService.read(id)).thenReturn(null);

        ResponseEntity<FunctionDTO> response = functionController.read(id);
        assertEquals(404, response.getStatusCodeValue());
        verify(functionService, times(1)).read(id);
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        FunctionDTO functionDTO = new FunctionDTO();
        functionDTO.setFunctionId(id);
        functionDTO.setType("Updated Function");

        when(functionService.update(any(FunctionDTO.class))).thenReturn(functionDTO);

        ResponseEntity<FunctionDTO> response = functionController.update(id, functionDTO);
        assertNotNull(response.getBody());
        assertEquals("Updated Function", response.getBody().getType());
        verify(functionService, times(1)).update(any(FunctionDTO.class));
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        doNothing().when(functionService).delete(id);

        ResponseEntity<Void> response = functionController.delete(id);
        assertEquals(200, response.getStatusCodeValue());
        verify(functionService, times(1)).delete(id);
    }
}