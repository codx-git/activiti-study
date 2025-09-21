package com.example.activiti.controller;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class CallbackController {
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public CallbackController(RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    @PostMapping("/callback")
    public void handleCallback(@RequestHeader("X-Process-Id") String processId,
                               @RequestBody String responseBody) {
        // 发送消息唤醒流程，并传递回调结果
        //runtimeService.createExecutionQuery().processDefinitionId(processId).messageEventSubscriptionName("httpResponseReceived");
        String executeId = runtimeService.createExecutionQuery().messageEventSubscriptionName("httpResponseReceived").processInstanceId(processId).singleResult().getId();
        runtimeService.messageEventReceived("httpResponseReceived", executeId,
                Collections.singletonMap("httpResponse", responseBody));
    }
}