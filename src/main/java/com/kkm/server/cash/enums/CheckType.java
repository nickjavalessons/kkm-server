package com.kkm.server.cash.enums;

public enum CheckType {
    SALE(0), REFUND_SALE(1);
    int value;

    CheckType(int value) {
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
