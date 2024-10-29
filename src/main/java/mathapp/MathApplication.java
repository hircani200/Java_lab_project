package mathapp;

import mathapp.DTO.FunctionPointDTO;

import mathapp.DTO.TaskDTO;
import mathapp.DTO.TaskResultDTO;
import mathapp.service.FunctionPointService;
import mathapp.service.FunctionService;
import mathapp.service.TaskResultService;
import mathapp.service.TaskService;

import mathapp.DTO.FunctionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class MathApplication implements CommandLineRunner {

    private final FunctionService functionService;
    private final FunctionPointService functionPointService;
    private final TaskService taskService;
    private final TaskResultService taskResultService;

    @Autowired
    public MathApplication(FunctionService functionService, FunctionPointService service, TaskService taskService, TaskResultService taskResultService) {
        this.functionService = functionService;
        this.functionPointService = service;
        this.taskService = taskService;
        this.taskResultService = taskResultService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MathApplication.class, args);
    }

    @Override
    public void run(String... args) {

        FunctionDTO createFunction = new FunctionDTO();
        createFunction.setFunctionId(1L);
        createFunction.setName("CosFunction");
        createFunction.setXFrom(0.0);
        createFunction.setXTo(0.993);
        createFunction.setCount(100);
        functionService.create(createFunction);

        FunctionPointDTO createFunctionPoint = new FunctionPointDTO();
        createFunctionPoint.setPointId(1L);
        createFunctionPoint.setFunctionId(1L);
        createFunctionPoint.setXValue(0.1);
        createFunctionPoint.setYValue(0.001);
        functionPointService.create(createFunctionPoint);

        TaskDTO createTask = new TaskDTO();
        createTask.setStartTime(LocalDateTime.now());
        createTask.setTaskId(1L);
        createTask.setFunctionId(1L);
        createTask.setStatus("Completed");
        createTask.setTaskType("Integration");
        createTask.setEndTime(LocalDateTime.now());
        taskService.create(createTask);

        TaskResultDTO createTaskResult = new TaskResultDTO();
        createTaskResult.setResultId(1L);
        createTaskResult.setTaskId(1L);
        createTaskResult.setResult(0.001);
        taskResultService.create(createTaskResult);



        FunctionDTO readFunction = functionService.read(1L);
        readFunction.setName("TanFunction");
        functionService.update(readFunction);

        FunctionPointDTO readFunctionPoint = functionPointService.read(1L);
        readFunctionPoint.setXValue(1.1);
        functionPointService.update(readFunctionPoint);

        TaskDTO readTask = taskService.read(1L);
        readTask.setStatus("Complete");
        taskService.update(readTask);

        TaskResultDTO readTaskResult = taskResultService.read(1L);
        readTaskResult.setResult(0.2);
        taskResultService.update(readTaskResult);



        functionService.delete(1L);
        functionPointService.delete(1L);
        taskService.delete(1L);
        taskResultService.delete(1L);
    }
}
