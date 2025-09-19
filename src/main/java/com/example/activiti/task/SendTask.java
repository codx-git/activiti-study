package com.example.activiti.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SendTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("发起异步查询任务开始---");
    }
}
