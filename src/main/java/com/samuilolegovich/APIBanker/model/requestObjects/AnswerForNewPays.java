package com.samuilolegovich.APIBanker.model.requestObjects;

public class AnswerForNewPays {
    private long payment_id;
    private String status;

    public AnswerForNewPays() {
    }

    public AnswerForNewPays(long payment_id, String status) {
        this.payment_id = payment_id;
        this.status = status;
    }

    public AnswerForNewPays(AnswerForNewPay answerForNewPay) {
        this.payment_id = answerForNewPay.getPayment_id();
        this.status = Status.ok.toString();
    }

    public long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(long payment_id) {
        this.payment_id = payment_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
