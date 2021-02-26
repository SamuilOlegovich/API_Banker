package com.samuilolegovich.APIBanker.model.requestObjects;

import com.samuilolegovich.APIBanker.model.db.PaymentsJournal;

public class AnswerForPaymentJournal {
    private long payment_id;
    private String timestamp;   // 2020-08-25 13:18:54
    private String src_acc_num;
    private String dest_acc_num;
    private double amount;
    private Payer payer;
    private Recipient recipient;



    public AnswerForPaymentJournal() {
    }

    public AnswerForPaymentJournal(PaymentsJournal paymentsJournal) {
        this.payment_id = paymentsJournal.getPayment_id();
        this.timestamp = paymentsJournal.getTimestamp();
        this.src_acc_num = paymentsJournal.getSrc_acc_num();
        this.dest_acc_num = paymentsJournal.getDest_acc_num();
        this.amount = paymentsJournal.getAmount();
        this.payer = null;
        this.recipient = null;
    }

    public long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(long payment_id) {
        this.payment_id = payment_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSrc_acc_num() {
        return src_acc_num;
    }

    public void setSrc_acc_num(String src_acc_num) {
        this.src_acc_num = src_acc_num;
    }

    public String getDest_acc_num() {
        return dest_acc_num;
    }

    public void setDest_acc_num(String dest_acc_num) {
        this.dest_acc_num = dest_acc_num;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
}
