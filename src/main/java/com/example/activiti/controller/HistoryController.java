package com.example.activiti.controller;

import com.example.activiti.common.ApiResponse;
import org.activiti.engine.HistoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("history")
public class HistoryController {

    private final HistoryService historyService;
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping("/query")
    public ApiResponse<Void> query(){
        historyService.createHistoricDetailQuery().taskId("");
        historyService.createHistoricActivityInstanceQuery();
        return ApiResponse.success(null);
    }
}
