package com.samuilolegovich.APIBanker.model.requestObjects;

public class AnswerForNewPays {
    private int payment_id;
    private String status;

    public AnswerForNewPays() {
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
