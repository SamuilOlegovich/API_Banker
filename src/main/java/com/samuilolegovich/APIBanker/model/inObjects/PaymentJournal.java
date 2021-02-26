package com.samuilolegovich.APIBanker.model.inObjects;

public class PaymentJournal {
    private long payer_id;
    private long recipient_id;
    private long source_acc_id;
    private long dest_acc_id;

    public PaymentJournal() {
    }



    public void setSource_acc_id(long source_acc_id) { this.source_acc_id = source_acc_id; }
    public void setRecipient_id(long recipient_id) { this.recipient_id = recipient_id; }
    public void setDest_acc_id(long dest_acc_id) { this.dest_acc_id = dest_acc_id; }
    public void setPayer_id(long payer_id) { this.payer_id = payer_id; }
    public long getSource_acc_id() { return source_acc_id; }
    public long getRecipient_id() { return recipient_id; }
    public long getDest_acc_id() { return dest_acc_id; }
    public long getPayer_id() { return payer_id; }

}
