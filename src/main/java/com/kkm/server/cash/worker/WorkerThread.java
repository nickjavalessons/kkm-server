package com.kkm.server.cash.worker;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kkm.server.cash.CashReceipt;
import com.kkm.server.cash.CheckEntityBuilder;
import com.kkm.server.cash.SessionState;
import com.kkm.server.cash.requests.KkmCheck;
import com.kkm.server.cash.requests.SimpleKkmRequest;
import com.kkm.server.cash.responce.KkmState;
import com.kkm.server.entity.CheckEntity;
import com.kkm.server.entity.KkmServer;
import com.kkm.server.utils.CheckMap;
import com.kkm.server.utils.CheckQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class WorkerThread implements Runnable {
    @Autowired
    private ApplicationContext context;

    @Autowired CheckQueue queue;

    @Autowired CheckMap checkMap;

    KkmServer server;

    public void setServer(KkmServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        CashReceipt cr = null;
        if(!isKkmAvalible()){
            System.out.println(server.getServerAddress() + " Недоступен");
            return;
        }
        while(!Thread.currentThread().isInterrupted()) {
            System.out.println("Thread name " + Thread.currentThread().getName());
            try {
                System.out.println("Queue size: " + queue.size());
                cr = queue.take();
                System.out.println("Queue size: " + queue.size() + " Id: " + cr.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            KkmCheck kkmCheck = context.getBean(KkmCheck.class);
            kkmCheck.build(cr);
            String response = registerCheck(kkmCheck);
            Gson g = new Gson();
            JsonObject responseJ = g.fromJson(response, JsonObject.class);
            CheckEntity checkEntity = new CheckEntityBuilder(kkmCheck.toJson(), responseJ).build();
            checkMap.putIfAbsent(checkEntity.getIdCommand(), checkEntity);
        }
    }
//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
    private KkmState openShift() throws RuntimeException{
        SimpleKkmRequest openShift = new SimpleKkmRequest(SimpleKkmRequest.SimpleCommand.OPEN_SHIFT);
        String res;
        KkmState kkmState = null;
        try {
            res = server.sendPost(openShift.toString());
            kkmState = new KkmState(res);
            if(!"\"\"".equals(kkmState.getError())) throw new RuntimeException(kkmState.getError());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Невозможно открыть смену");

        }
        return kkmState;
    }
    private KkmState closeShift() throws RuntimeException{
        SimpleKkmRequest openShift = new SimpleKkmRequest(SimpleKkmRequest.SimpleCommand.Z_REPORT);
        String res;
        KkmState kkmState = null;
        try {
            res = server.sendPost(openShift.toString());
            kkmState = new KkmState(res);
            if(!"\"\"".equals(kkmState.getError())) throw new RuntimeException(kkmState.getError());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Невозможно закрыть смену ");
        }
        return kkmState;
    }
    private KkmState getKkmState() throws RuntimeException{
        SimpleKkmRequest dataKKT = new SimpleKkmRequest(SimpleKkmRequest.SimpleCommand.DATA_KKT);
        KkmState kkmState = null;
        String res;
        try {
            res = server.sendPost (dataKKT.toString());
            kkmState = new KkmState(res);
            System.out.println("Error " + kkmState.getError());
            if(!"\"\"".equals(kkmState.getError())) throw new RuntimeException(kkmState.getError());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Не возможно получить состояние ККМ");
        }
        return kkmState;
    }
    private boolean isKkmAvalible(){
        try {
            KkmState kkmState = getKkmState();
            if(kkmState.getSessionState() == SessionState.CLOSED){
                System.out.println("Session Closed");
                openShift();
            } else if(kkmState.getSessionState() == SessionState.EXPIRED){
                System.out.println("Session EXPIRED");
                closeShift();
                openShift();
            }
            return true;
        } catch (RuntimeException e){
            e.printStackTrace();
            return false;
        }
    }
    private String registerCheck(KkmCheck kkmCheck) throws RuntimeException {
        if(!isKkmAvalible())throw new RuntimeException();
        String res;
        try {
            res = server.sendPost(kkmCheck.toString());
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Невозможно пробить чек");
        }
        return res;
    }




























}
