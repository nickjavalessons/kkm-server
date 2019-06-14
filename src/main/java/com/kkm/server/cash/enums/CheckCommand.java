package com.kkm.server.cash.enums;

public enum CheckCommand {
    REGISTER_CHECK("RegisterCheck");
    String value;

    CheckCommand(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
