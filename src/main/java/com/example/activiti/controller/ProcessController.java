package com.example.activiti.controller;


import com.example.activiti.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process")
@Slf4j
public class ProcessController {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;

    public ProcessController(RepositoryService repositoryService, RuntimeService runtimeService) {
        this.repositoryService = repositoryService;
        this.runtimeService = runtimeService;
    }

    @PostMapping("/query")
    public ApiResponse<String> query(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        return ApiResponse.success(list.toString());
    }

    @PostMapping("/start")
    public ApiResponse<Void> start(@RequestParam String id){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).list();
        if(list.isEmpty()) return ApiResponse.fail(502,"未找到对应可执行任务");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(id);
        log.info("流程实例：{}",processInstance);
        return ApiResponse.success(null);
    }

    @PostMapping("/queryMessage")
    public ApiResponse<String> qeuryMessage(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().messageEventSubscriptionName("httpResponseReceived").list();
        return ApiResponse.success(list.toString());
    }
}
