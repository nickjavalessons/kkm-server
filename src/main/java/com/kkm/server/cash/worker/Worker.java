package com.kkm.server.cash.worker;

import com.kkm.server.entity.KkmServer;
import com.kkm.server.repository.KkmServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Worker {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    KkmServerRepository kkmServerRepository;

    @PostConstruct
    public void start(){
        for(KkmServer kkmServer: kkmServerRepository.findAll()){
            WorkerThread thread = context.getBean(WorkerThread.class);
            thread.setServer(kkmServer);
            taskExecutor.execute(thread);
        }
    }
}
