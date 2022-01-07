package org.moneydrop.app.DataClasses;

public class WithdrawDataset {
    String amount;
    String number;
    String method;
    String uid;

    public WithdrawDataset() {
    }

    public WithdrawDataset(String amount, String number, String method, String uid) {
        this.amount = amount;
        this.number = number;
        this.method = method;
        this.uid = uid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
