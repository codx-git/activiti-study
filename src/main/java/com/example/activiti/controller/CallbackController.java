package com.example.activiti.controller;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class CallbackController {
    private final RuntimeService runtimeService;

    public CallbackController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @PostMapping("/callback")
    public void handleCallback(@RequestHeader("X-Process-Id") String processId,
                               @RequestBody String responseBody) {
        // 发送消息唤醒流程，并传递回调结果
        runtimeService.getProcessInstanceEvents(processId);

        runtimeService.messageEventReceived("httpResponseReceived", processId,
                Collections.singletonMap("httpResponse", responseBody));
    }
}