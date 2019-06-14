package com.kkm.server.cash;

import com.kkm.server.entity.Tovar;

public class CashReceiptRow {
    private Tovar tovar;
    private int quantity;

    public CashReceiptRow(Tovar tovar, int quantity) {
        this.tovar = tovar;
        this.quantity = quantity;
    }

    public Tovar getTovar() {
        return tovar;
    }

    protected void setTovar(Tovar tovar) {
        this.tovar = tovar;
    }

    public int getQuantity() {
        return quantity;
    }

    protected void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
