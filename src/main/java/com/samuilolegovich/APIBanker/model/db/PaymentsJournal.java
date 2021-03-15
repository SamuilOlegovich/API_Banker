package com.samuilolegovich.APIBanker.model.db;


import com.samuilolegovich.APIBanker.model.inObjects.NewPay;
import javax.persistence.*;


@Entity
public class PaymentsJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private long paymentId;

    @Column(name = "timestamp")
    private String timeStamp;           // время совершения платежа
    private double amount;
    @Column(name = "source_acc_id")
    private long sourceAccId;           // источник платежа
    @Column(name = "src_acc_num")
    private String srcAccNum;           // счет клиента с которого списались деньги
    @Column(name = "dest_acc_id")
    private long destAccId;             // получатель платежа
    @Column(name = "dest_acc_num")
    private String destAccNum;          // счет клиента на который пришли деньги
    private String reason;              // назначение платежа



    public PaymentsJournal() {
    }

    public PaymentsJournal(double amount, long sourceAccId, String srcAccNum,
                           long destAccId, String destAccNum, String reason) {
        this.amount = amount;
        this.sourceAccId = sourceAccId;
        this.srcAccNum = srcAccNum;
        this.destAccId = destAccId;
        this.destAccNum = destAccNum;
        this.reason = reason;
    }

    public PaymentsJournal(Accounts source, Accounts dest, NewPay newPay) {
        this.amount = newPay.getAmount();
        this.sourceAccId = source.getClientId();
        this.srcAccNum = source.getAccountNum();
        this.destAccId = dest.getClientId();
        this.destAccNum = dest.getAccountNum();
        this.reason = newPay.getReason();
    }

    public PaymentsJournal(NewPay newPay) {
        this.amount = newPay.getAmount();
        this.sourceAccId = newPay.getSource_acc_id();
        this.srcAccNum = null;
        this.destAccId = newPay.getDest_acc_id();
        this.destAccNum = null;
        this.reason = newPay.getReason();
    }

    public void setPaymentId(long paymentId) { this.paymentId = paymentId; }
    public void setReason(String reason) { this.reason = reason; }
    public String getDestAccNum() { return destAccNum; }
    public String getSrcAccNum() { return srcAccNum; }
    public String getTimeStamp() { return timeStamp; }
    public long getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public String getReason() { return reason; }
}
