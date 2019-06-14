package com.kkm.server.controllers;

import com.google.gson.Gson;
import com.kkm.server.entity.CheckEntity;
import com.kkm.server.repository.CheckEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManageController {
    @Autowired
    private CheckEntityRepository checkEntityRepository;

    @GetMapping("/bydate")
    @RequestMapping(method = RequestMethod.GET)
    public String getChecksByDate(@RequestParam("date") String date){
        List<CheckEntity> checkEntityList = checkEntityRepository.findByDateS(date);
        checkEntityList.stream().forEach(ce->ce.getStrings().stream().forEach(ces->ces.setCheckEntity(null)));
        Gson g = new Gson();
        String jsonList = g.toJson(checkEntityList);
        return jsonList;
    }
}
