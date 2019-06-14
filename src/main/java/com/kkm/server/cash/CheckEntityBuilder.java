package com.kkm.server.cash;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kkm.server.entity.CheckEntity;
import com.kkm.server.entity.CheckString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckEntityBuilder {
    JsonObject request;
    JsonObject response;

    public CheckEntityBuilder(JsonObject request, JsonObject response) {
        this.request = request;
        this.response = response;
    }

    public CheckEntity build(){
        CheckEntity checkEntity = new CheckEntity();
        String command = request.get("Command").getAsString();
        checkEntity.setCommand(command);
        int numDevice = request.get("NumDevice").getAsInt();
        checkEntity.setNumDevice(numDevice);
        int timeout = request.get("Timeout").getAsInt();
        checkEntity.setTimeout(timeout);
        String idCommand = request.get("IdCommand").getAsString();
        checkEntity.setIdCommand(idCommand);
        boolean isFiscalCheck = request.get("IsFiscalCheck").getAsBoolean();
        checkEntity.setFiscalCheck(isFiscalCheck);
        int typeCheck = request.get("TypeCheck").getAsInt();
        checkEntity.setTypeCheck(typeCheck);
        boolean cancelOpenedCheck = request.get("CancelOpenedCheck").getAsBoolean();
        checkEntity.setCancelOpenedCheck(cancelOpenedCheck);
        boolean notPrint = request.get("NotPrint").getAsBoolean();
        checkEntity.setNotPrint(notPrint);
        int numberOfCopies = request.get("NumberCopies").getAsInt();
        checkEntity.setNumberOfCopies(numberOfCopies);
        String cashierName = request.get("CashierName").getAsString();
        checkEntity.setCashierName(cashierName);
        String cashierVATIN = request.get("CashierVATIN").getAsString();
        checkEntity.setCashierVATIN(cashierVATIN);
        String clientAddress = request.get("ClientAddress").getAsString();
        checkEntity.setClientAddress(clientAddress);
        boolean payByProcessing = request.get("PayByProcessing").getAsBoolean();
        checkEntity.setPayByProcessing(payByProcessing);
        double cash = request.get("Cash").getAsDouble();
        checkEntity.setCash(cash);
        double electronicPayment = request.get("ElectronicPayment").getAsDouble();
        checkEntity.setElectronicPayment(electronicPayment);
        double advancePayment = request.get("AdvancePayment").getAsDouble();
        checkEntity.setAdvancePayment(advancePayment);
        double credit = request.get("Credit").getAsDouble();
        checkEntity.setCredit(credit);
        double cashProvision = request.get("CashProvision").getAsDouble();
        checkEntity.setCashProvision(cashProvision);
        int checkNumber = response.get("CheckNumber").getAsInt();
        checkEntity.setCheckNumber(checkNumber);
        int sessionNumber = response.get("SessionNumber").getAsInt();
        checkEntity.setSessionNumber(sessionNumber);
        int sessionCheckNumber = response.get("SessionCheckNumber").getAsInt();
        checkEntity.setSessionCheckNumber(sessionCheckNumber);
        String qrCode = response.get("QRCode").getAsString();
        checkEntity.setQrCode(qrCode);
        String error = response.get("Error").getAsString();;
        checkEntity.setError(error);
        int status  = response.get("Status").getAsInt();
        checkEntity.setStatus(status);
        QRCode qrCode1 = new QRCode(qrCode);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        LocalDateTime dateTime = LocalDateTime.parse(qrCode1.getT().replaceAll("T", " "), formatter);
        long date = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        checkEntity.setDate(date);
        LocalDate localDate = dateTime.toLocalDate();
        checkEntity.setDateS(localDate.toString());
        LocalTime localTime = dateTime.toLocalTime();
        checkEntity.setTimeS(localTime.toString());
        JsonArray strings = request.getAsJsonArray("CheckStrings");
        List<CheckString> checkStringList = new ArrayList<>();
        for(JsonElement element: strings){
            CheckString checkString = new CheckString();
            JsonObject checkStringJson = element.getAsJsonObject().get("Register").getAsJsonObject();
            String name = checkStringJson.get("Name").getAsString();
            checkString.setName(name);
            double quantity = checkStringJson.get("Quantity").getAsDouble();
            checkString.setQuantity(quantity);
            double price = checkStringJson.get("Price").getAsDouble();
            checkString.setPrice(price);
            double amount = checkStringJson.get("Amount").getAsDouble();
            checkString.setAmount(amount);
            int department = checkStringJson.get("Department").getAsInt();
            checkString.setDepartment(department);
            int tax = checkStringJson.get("Tax").getAsInt();
            checkString.setTax(tax);
            int signMethodCalculation = checkStringJson.get("SignMethodCalculation").getAsInt();
            checkString.setSignMethodCalculation(signMethodCalculation);
            int signCalculationObject = checkStringJson.get("SignCalculationObject").getAsInt();
            checkString.setSignCalculationObject(signCalculationObject);
            checkStringList.add(checkString);

            checkString.setCheckEntity(checkEntity);
        }
        checkEntity.setStrings(checkStringList);
        return checkEntity;
    }
}
