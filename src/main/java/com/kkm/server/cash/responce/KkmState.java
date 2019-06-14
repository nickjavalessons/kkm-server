package com.kkm.server.cash.responce;


import com.kkm.server.cash.SessionState;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class KkmState implements KkmResponce {
    private int sessionState;
    private String error;

    public KkmState(String jsonString){
        build(jsonString);
    }
    public void build(String jsonString){
        System.out.println("jsonString: " + jsonString);
        JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class).getAsJsonObject();
        try { sessionState = Integer.parseInt(jsonObject.getAsJsonObject("Info").get("SessionState").toString());}
        catch (NullPointerException e){sessionState = SessionState.UNKNOWN;}
        try{  error = jsonObject.get("Error").toString();}
        catch (NullPointerException e){ error = "";}
    }
    public int getSessionState() {
        return sessionState;
    }

    public String getError() {
        return error;
    }
}
