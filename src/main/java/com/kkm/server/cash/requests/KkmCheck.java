package com.kkm.server.cash.requests;

import com.kkm.server.cash.CashConfig;
import com.kkm.server.cash.CashReceipt;
import com.kkm.server.cash.CashReceiptRow;
import com.kkm.server.cash.enums.CheckType;
import com.kkm.server.cash.enums.SignCalculationObject;
import com.kkm.server.cash.enums.SignMethodCalculation;
import com.kkm.server.entity.Discount;
import com.kkm.server.utils.RoundDouble;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class KkmCheck implements KkmRequest {
    //UserProperties userProperties = UserProperties.getInstance();
    @Autowired
    CashConfig cashConfig;
    private final String COMMAND = "RegisterCheck";
    private int numDevice;
    private int timeout;
    private String idCommand;
    private boolean isFiscalCheck;
    private CheckType checkType;
    private boolean notPrint;
    private String cashierName;
    private String cashierVATIN;
    private String clientAddress;
    private List<CashString> cashStringList;
    private double cash = 0;
    private double electronicPayment = 0;
    private double advancePayment = 0;
    private double credit = 0;
    private double cashProvision = 0;

    public KkmCheck() {
    }

    public void build(CashReceipt cashReceipt) throws IllegalArgumentException{
        numDevice = cashConfig.getCheckNumDevice();
        timeout = cashConfig.getCheckTimeout();
        idCommand = cashReceipt.getId();
        isFiscalCheck = cashConfig.isFiscalCheck();
        checkType = cashReceipt.getCheckType();
        notPrint = cashConfig.isNotPrint();
//        cashierName=userProperties.getProperty("Kkm.cashier.name");
//        cashierVATIN=userProperties.getProperty("Kkm.cashier.inn");
        cashierName = cashConfig.getCashierName();
        cashierVATIN = cashConfig.getCashierInn();
        clientAddress = cashReceipt.getClientAddress();
        List<CashReceiptRow> checkStrings = cashReceipt.getRows();
        cashStringList = createCashStrings(checkStrings, cashReceipt.getDiscount());
        cashStringList.stream().forEach(l->electronicPayment += l.getAmount());
        System.out.println("Чек собран");
    }

    private List<CashString> createCashStrings(List<CashReceiptRow> cashReceiptRows, Discount discount){
        List<CashString> cashStringList = new ArrayList<>();
        for(CashReceiptRow crr: cashReceiptRows){
            CashString cashString = new CashString();
            cashString.setName(crr.getTovar().getArticle() + ":" + crr.getTovar().getDescription());
            cashString.setQuantity(crr.getQuantity());
            cashString.setPrice(crr.getTovar().getPrice());
            cashString.setDiscount(discount);
            cashString.setDepartment(cashConfig.getDepartment());
//            cashString.setTax(Integer.parseInt(userProperties.getProperty("Kkm.check.tax")));
            cashString.setTax(cashConfig.getCheckTax());
            SignMethodCalculation signMethodCalculation =
                    SignMethodCalculation.valueOf(cashConfig.getSignMethodCalculation());
            cashString.setSignMethodCalculation(signMethodCalculation);
            SignCalculationObject signCalculationObject =
                    SignCalculationObject.valueOf(cashConfig.getSignCalculationObject());
            cashString.setSignCalculationObject(signCalculationObject);
            cashStringList.add(cashString);
        }
        return cashStringList;
    }
    @Override
    public JsonObject toJson() {
        JsonObject rootObject = new JsonObject();
        rootObject.addProperty("Command", "RegisterCheck");
        rootObject.addProperty("NumDevice", numDevice);
        rootObject.addProperty("Timeout", timeout);
        rootObject.addProperty("IdCommand", idCommand);
        rootObject.addProperty("IsFiscalCheck", true);
        rootObject.addProperty("TypeCheck", checkType.getValue());
        rootObject.addProperty("CancelOpenedCheck", true);
        rootObject.addProperty("NotPrint", notPrint);
        rootObject.addProperty("NumberCopies", 1);
        rootObject.addProperty("CashierName", cashierName);
        rootObject.addProperty("CashierVATIN", cashierVATIN);
        rootObject.addProperty("ClientAddress", clientAddress);
        //rootObject.addProperty("TaxVariant", kkmCheck.getTaxVariant());
        rootObject.addProperty("PayByProcessing", false);
        JsonArray checkStrings = new JsonArray();
        for(CashString cs:cashStringList){
            checkStrings.add(cs.toJson());
        }
        rootObject.add("CheckStrings", checkStrings);

        rootObject.addProperty("Cash", cash);
        rootObject.addProperty("ElectronicPayment", electronicPayment);
        rootObject.addProperty("AdvancePayment",advancePayment);
        rootObject.addProperty("Credit", credit);
        rootObject.addProperty("CashProvision",  cashProvision);
        return rootObject;
    }

    @Override
    public String toString(){
        return toJson().toString();
    }
    private static class CashString{
        private String type = "Register";
        private String name;
        private double quantity;
        private double price;
        private int department;
        private int tax;
        private SignMethodCalculation signMethodCalculation;
        private SignCalculationObject signCalculationObject;
        private Discount discount;

        public JsonObject toJson() {
            JsonObject register = new JsonObject();
            JsonObject check = new JsonObject();
            check.addProperty("Name", name);
            check.addProperty("Quantity", quantity);
            check.addProperty("Price", price);
            check.addProperty("Amount", getAmount());
            check.addProperty("Department", department);
            check.addProperty("Tax", tax);
            check.addProperty("SignMethodCalculation", signMethodCalculation.getValue());
            check.addProperty("SignCalculationObject", signCalculationObject.getValue());
            register.add("Register", check);
            return register;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public double getQuantity() {
            return quantity;
        }
        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public double getAmount() {
            return RoundDouble.roundDown(price*quantity*discount.getValue());
        }
        public int getDepartment() {
            return department;
        }
        public void setDepartment(int department) {
            this.department = department;
        }
        public int getTax() {
            return tax;
        }
        public void setTax(int tax) {
            this.tax = tax;
        }
        public SignMethodCalculation getSignMethodCalculation() {
            return signMethodCalculation;
        }
        public void setSignMethodCalculation(SignMethodCalculation signMethodCalculation) {
            this.signMethodCalculation = signMethodCalculation;
        }
        public SignCalculationObject getSignCalculationObject() {
            return signCalculationObject;
        }
        public void setSignCalculationObject(SignCalculationObject signCalculationObject) {
            this.signCalculationObject = signCalculationObject;
        }

        public Discount getDiscount() {
            return discount;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }
    }
}
