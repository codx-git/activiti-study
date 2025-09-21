package com.example.activiti.controller;

import com.example.activiti.common.ApiResponse;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("query")
    public ApiResponse<Void> query(@RequestParam String name){
        List<Task> list =  taskService.createTaskQuery().processDefinitionName(name).list();
        return ApiResponse.success(null);
    }

}
