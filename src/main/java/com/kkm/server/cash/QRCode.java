package com.kkm.server.cash;

import java.util.HashMap;
import java.util.Map;

public class QRCode {
    private String t;
    private String s;
    private String fn;
    private String i;
    private String fp;
    private String n;
    public QRCode(String qrCode){
        String[] fields = qrCode.split("&");
        Map<String, String > fieldsMap = new HashMap<>();
        for(String str: fields){
            String[] field = str.split("=");
            if(field.length > 1){
                String key = field[0].trim();
                String value = field[1].trim();
                fieldsMap.put(key, value);
            }
        }
        setT(fieldsMap.get("t"));
        setS(fieldsMap.get("s"));
        setFn(fieldsMap.get("fn"));
        setI(fieldsMap.get("i"));
        setFp(fieldsMap.get("fp"));
        setN(fieldsMap.get("n"));
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getFp() {
        return fp;
    }

    public void setFp(String fp) {
        this.fp = fp;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }
}
