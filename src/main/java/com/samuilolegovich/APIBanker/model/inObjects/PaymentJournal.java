package com.samuilolegovich.APIBanker.model.inObjects;

public class PaymentJournal {
    private int payer_id;
    private int recipient_id;
    private int source_acc_id;
    private int dest_acc_id;

    public PaymentJournal() {
    }

    public int getPayer_id() {
        return payer_id;
    }

    public void setPayer_id(int payer_id) {
        this.payer_id = payer_id;
    }

    public int getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(int recipient_id) {
        this.recipient_id = recipient_id;
    }

    public int getSource_acc_id() {
        return source_acc_id;
    }

    public void setSource_acc_id(int source_acc_id) {
        this.source_acc_id = source_acc_id;
    }

    public int getDest_acc_id() {
        return dest_acc_id;
    }

    public void setDest_acc_id(int dest_acc_id) {
        this.dest_acc_id = dest_acc_id;
    }
}
