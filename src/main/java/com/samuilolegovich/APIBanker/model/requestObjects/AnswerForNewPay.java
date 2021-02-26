package com.samuilolegovich.APIBanker.model.requestObjects;

public class AnswerForNewPay {
    private int payment_id;

    public AnswerForNewPay() {
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }
}
