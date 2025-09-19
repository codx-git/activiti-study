package com.example.activiti.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class testTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        // 在这里编写业务逻辑
        System.out.println("执行服务任务逻辑...");
        // 可以通过execution获取流程变量
        String param = (String) execution.getVariable("param");
        System.out.println("接收到的参数：" + param);
    }
}
