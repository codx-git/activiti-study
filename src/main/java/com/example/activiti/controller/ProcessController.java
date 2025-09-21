package com.example.activiti.controller;


import com.example.activiti.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
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
    public ApiResponse<String> queryMessage(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().messageEventSubscriptionName("httpResponseReceived").list();
        return ApiResponse.success(list.toString());
    }

    @PostMapping("/queryExeMessage")
    public ApiResponse<String> queryExeMessage(@RequestParam String name){
        List<Execution> list = runtimeService.createExecutionQuery().messageEventSubscriptionName(name).list();
        return ApiResponse.success(list.toString());
    }

    @PostMapping("getCurrentExe")
    public ApiResponse<Execution> getCurrentExe(@RequestParam String processInstanceId){
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
        if(executions.isEmpty()) return ApiResponse.fail(502,"不存在");
        for (Execution execution : executions) {
            // 有activityId的执行对象才是当前正在执行的节点
            String activityId = execution.getActivityId();
            if (activityId != null) {
                 log.info("当前节点信息：{}",activityId); // 返回第一个找到的活动节点
            }
        }
        return ApiResponse.success(null);
    }
}
