package com.kkm.server.cash.requests;

import com.kkm.server.cash.CashConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kkm.server.utils.Guid;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleKkmRequest implements KkmRequest{
    private CashConfig cashConfig;

    private Map<String, String> propertiesMap;

    public SimpleKkmRequest(SimpleKkmRequest.SimpleCommand command){
        cashConfig = new CashConfig();
        System.out.println("cashConfig" + cashConfig);
        String cashier = cashConfig.getCashierName();
        String cashierVATIN = cashConfig.getCashierInn();
        propertiesMap = new LinkedHashMap<>();
        propertiesMap.put("Command", command.getValue());
        propertiesMap.put("NumDevice", "0");
        propertiesMap.put("IdCommand", Guid.guid());

        propertiesMap.put("IdDevice", "");
        propertiesMap.put("CashierName", cashier);
        propertiesMap.put("CashierVATIN", cashierVATIN);
    }

    @Override
    public JsonObject toJson() throws IllegalArgumentException{
        if(isValid()){
            JsonObject rootObject = new JsonObject();
            for(Map.Entry<String, String> entry: propertiesMap.entrySet()){
                rootObject.addProperty(entry.getKey(), entry.getValue());
            }
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

    public enum SimpleCommand{

        DATA_KKT("GetDataKKT"), OPEN_SHIFT("OpenShift"), CLOSE_SHIFT("CloseShift"), Z_REPORT("ZReport");
        String value;

        SimpleCommand(String value) {
            this.value = value;
        }
        public String getValue(){
            return value;
        }
    }
}
