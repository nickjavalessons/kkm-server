package com.kkm.server.cash;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "kkm.config")
@Configuration
public class CashConfig {
    String cashierName;
    String cashierInn;
    int checkTimeout;
    int checkNumDevice;
    int checkTax;
    boolean isFiscalCheck;
    boolean notPrint;
    int department;
    String signCalculationObject;
    String signMethodCalculation;

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getCashierInn() {
        return cashierInn;
    }

    public void setCashierInn(String cashierInn) {
        this.cashierInn = cashierInn;
    }

    public int getCheckTimeout() {
        return checkTimeout;
    }

    public void setCheckTimeout(int checkTimeout) {
        this.checkTimeout = checkTimeout;
    }

    public int getCheckNumDevice() {
        return checkNumDevice;
    }

    public void setCheckNumDevice(int checkNumDevice) {
        this.checkNumDevice = checkNumDevice;
    }

    public int getCheckTax() {
        return checkTax;
    }

    public void setCheckTax(int checkTax) {
        this.checkTax = checkTax;
    }

    public boolean isFiscalCheck() {
        return isFiscalCheck;
    }

    public void setFiscalCheck(boolean fiscalCheck) {
        isFiscalCheck = fiscalCheck;
    }

    public boolean isNotPrint() {
        return notPrint;
    }

    public void setNotPrint(boolean notPrint) {
        this.notPrint = notPrint;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getSignCalculationObject() {
        return signCalculationObject;
    }

    public void setSignCalculationObject(String signCalculationObject) {
        this.signCalculationObject = signCalculationObject;
    }

    public String getSignMethodCalculation() {
        return signMethodCalculation;
    }

    public void setSignMethodCalculation(String signMethodCalculation) {
        this.signMethodCalculation = signMethodCalculation;
    }
}
