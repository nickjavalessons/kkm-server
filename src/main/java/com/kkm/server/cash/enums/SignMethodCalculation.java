package com.kkm.server.cash.enums;

public enum SignMethodCalculation {
    PREPAY_FULL(1), PREPAY(2), PREPAID_EXPENSE(3), FULL_SETTLEMENT(4), PART_SETTLEMENT_AND_CREDIT(5), CREDIT_TRANSFER(6), CREDIT_PAY(7);
    int value;

    SignMethodCalculation(int value) {
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
