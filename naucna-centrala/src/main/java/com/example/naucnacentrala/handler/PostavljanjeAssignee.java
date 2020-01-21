package com.example.naucnacentrala.handler;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostavljanjeAssignee implements TaskListener {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void notify(DelegateTask delegateTask) {

        System.out.println("usao u PostavljanjeAssignee");

        String username = (String) runtimeService.getVariable(delegateTask.getProcessInstanceId(), "glavniUrednikUsername");
        System.out.println("glavni urednik username: " + username);

        delegateTask.setAssignee(username);

        System.out.println("izasao iz PostavljanjeAssignee");

    }
}
