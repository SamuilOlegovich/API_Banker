package com.samuilolegovich.APIBanker.model.inObjects;

public class NewPay {
    private long source_acc_id;
    private long dest_acc_id;
    private double amount;
    private String reason;  // назначение платежа

    public NewPay() {
    }

    public long getSource_acc_id() {
        return source_acc_id;
    }

    public void setSource_acc_id(long source_acc_id) {
        this.source_acc_id = source_acc_id;
    }

    public long getDest_acc_id() {
        return dest_acc_id;
    }

    public void setDest_acc_id(long dest_acc_id) {
        this.dest_acc_id = dest_acc_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
