package com.kkm.server.cash;

import com.kkm.server.cash.enums.CheckType;
import com.kkm.server.entity.Discount;
import com.kkm.server.entity.Tovar;
import com.kkm.server.repository.DiscountRepository;
import com.kkm.server.repository.TovarRepository;
import com.kkm.server.utils.Guid;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class CashReceipt { //данные для создания кассового чека
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private TovarRepository tovarRepository;
    private JsonObject clientRequest;
    private String id;
    private Discount discount;
    private List<CashReceiptRow> rows;
    private String clientAddress;
    private CheckType checkType;

    public CashReceipt() {
        this.id = Guid.guid();
    }

    public void build(JsonObject jsonObject) throws IllegalArgumentException {
        System.out.println("CashReceipt build");
        this.clientRequest = jsonObject;
        String discountString = clientRequest.get("Discount").toString().replaceAll("\"","");
        setDiscount(discountString);
        JsonArray tovarArray = clientRequest.get("Tovar").getAsJsonArray();
        setRows(tovarArray);
        String clientAddress = clientRequest.get("ClientAddress").toString().replaceAll("\"","");
        setClientAddress(clientAddress);
        CheckType checkType = CheckType.valueOf(clientRequest.get("checkType").toString().replaceAll("\"",""));
        setCheckType(checkType);
    }

    public void setDiscount(String discountString) throws IllegalArgumentException {
        System.out.println("CashReceipt setDiscount " + discountString);
        if(discountString == null) throw new IllegalArgumentException("Discount null");
        System.out.println(discountRepository);
        List<Discount> discounts = discountRepository.findByName(discountString);
        if(discounts.size() == 0) throw  new IllegalArgumentException("Discount not found");
        System.out.println("discounts " + discounts);
        this.discount = discounts.get(0);
    }

    public void setRows(JsonArray jsonElements){
        rows = new ArrayList<>();
        for(JsonElement row: jsonElements){
            String tovarArticle = row.getAsJsonObject().get("article").toString().replaceAll("\"","");
            if(tovarArticle == null)  throw new IllegalArgumentException("tovar Article is null");
            List<Tovar> tovarList = tovarRepository.findByArticle(tovarArticle);
            if(tovarList.size() == 0) throw new IllegalArgumentException("tovar not found");
            Tovar tovar = tovarList.get(0);
            int quantity = Integer.parseInt(row.getAsJsonObject().get("quantity").toString().replaceAll("\"",""));
            CashReceiptRow crr = new CashReceiptRow(tovar, quantity);
            rows.add(crr);
        }
    }

    public List<CashReceiptRow> getRows() {
        return rows;
    }

    public Discount getDiscount(){
        return this.discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    public JsonObject getClientRequest() {
        return clientRequest;
    }

    public void setClientRequest(JsonObject clientRequest) {
        this.clientRequest = clientRequest;
    }

    public JsonObject toJson(){
        clientRequest.addProperty("id", id);
        return clientRequest;
    }

}
