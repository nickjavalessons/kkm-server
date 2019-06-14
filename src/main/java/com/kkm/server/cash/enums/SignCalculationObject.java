package com.kkm.server.cash.enums;

public enum SignCalculationObject {
    PRODUCT(1), EXCISE_PRODUCT(2), WORK(3), SERVICE(4);
    int value;

    SignCalculationObject(int value) {
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
