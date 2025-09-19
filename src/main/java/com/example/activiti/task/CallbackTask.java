package com.example.activiti.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CallbackTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("接口回调开始");
    }
}
