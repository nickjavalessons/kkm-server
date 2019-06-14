package com.kkm.server.controllers;

import com.kkm.server.cash.delivery.Sender;
import com.kkm.server.entity.CheckEntity;
import com.kkm.server.repository.CheckEntityRepository;
import com.kkm.server.utils.CheckMap;
import com.kkm.server.utils.CheckQueue;
import com.kkm.server.cash.CashReceipt;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ChekController {

    @Autowired
    private ApplicationContext context;

    @Autowired private CheckQueue checkQueue;
    @Autowired private CheckMap checkMap;
    @Autowired private CheckEntityRepository checkEntityRepository;

    @GetMapping("/check")
    @RequestMapping(method = RequestMethod.POST)
    public String getRegisteredCheck(@RequestBody String req){
        Gson g = new Gson();
        JsonObject jsonObject = g.fromJson(req, JsonObject.class);
        CashReceipt cr = context.getBean(CashReceipt.class);
        try {
            cr.build(jsonObject);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            //TODO: ответить клиенту ошибкой
        }

        try {
            checkQueue.put(cr);
        } catch (InterruptedException e) {
            //TODO:  ответить клиенту о невозможности совершения платежа
            e.printStackTrace();
        }
        while (!checkMap.containsKey(cr.getId())){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Waiting for check " + cr.getId());
        }
        CheckEntity checkEntity = checkMap.get(cr.getId());
        checkMap.remove(cr.getId());
        checkEntityRepository.save(checkEntity);
        try {
            Sender sender = new Sender(checkEntity);
            sender.send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return checkEntity.getQrCode();
    }
}
