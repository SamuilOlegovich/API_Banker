package com.samuilolegovich.APIBanker.model.requestObjects;

import com.samuilolegovich.APIBanker.model.db.PaymentsJournal;

public class AnswerForNewPay {
    private long payment_id;

    public AnswerForNewPay() {
    }

    public AnswerForNewPay(long payment_id) {
        this.payment_id = payment_id;
    }

    public AnswerForNewPay(PaymentsJournal paymentsJournal) {
        this.payment_id = paymentsJournal.getPaymentId();
    }

    public void setPayment_id(long payment_id) { this.payment_id = payment_id; }
    public long getPayment_id() { return payment_id; }
}
