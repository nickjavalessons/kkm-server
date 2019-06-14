package com.kkm.server.cash.requests;

import com.kkm.server.cash.CashConfig;
import com.kkm.server.utils.Guid;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

public class OpenShift implements KkmRequest {
    @Autowired
    CashConfig cashConfig;

    Map<String, String> propertiesMap;
    public OpenShift(){
//        UserProperties userProperties = UserProperties.getInstance();
//        String cashier = userProperties.getProperty("Kkm.cashier.name");
//        String cashierVATIN = userProperties.getProperty("Kkm.cashier.inn");
        CashConfig cashConfig = new CashConfig();
        String cashier = cashConfig.getCashierName();
        String cashierVATIN = cashConfig.getCashierInn();
        propertiesMap = new LinkedHashMap<>();
        propertiesMap.put("Command",  "OpenShift");
        propertiesMap.put("NumDevice", "0");
        propertiesMap.put("IdDevice", "");
        propertiesMap.put("CashierName", cashier);
        propertiesMap.put("CashierVATIN", cashierVATIN);
        propertiesMap.put("IdCommand", Guid.guid());
    }

    @Override
    public JsonObject toJson() throws IllegalArgumentException{
        if(isValid()){
            JsonObject rootObject = new JsonObject();
            for(Map.Entry<String, String> entry: propertiesMap.entrySet()){
                rootObject.addProperty(entry.getKey(), entry.getValue());
            }
//            Gson gson = new Gson();
            return rootObject;
        } else throw new IllegalArgumentException(); //TODO: уточнить что пошло не так
    }

    @Override
    public String toString(){
        return toJson().toString();
    }

    private boolean isValid() throws IllegalArgumentException{ //TODO: подумать. Выглядит не оч.
        return true;
    }
}
