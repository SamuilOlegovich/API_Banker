package com.samuilolegovich.APIBanker.model.db;


import com.samuilolegovich.APIBanker.model.inObjects.NewPay;

import javax.persistence.*;


@Entity
public class PaymentsJournal {
    // класс отвечает за таблицу
    // (если такой таблицы нет то при первом запуске он ее создаст)

    @Id // @ID - Важно чтобы была из библиотеке -> javax.persistence.Id (айди для уникального индификатора в таблице)
    @GeneratedValue(strategy = GenerationType.AUTO) // позволит генирировать при каждом добавлении новый айди
    private long payment_id;

    private String timestamp;   // время совершения платежа
    private double amount;
    @Column(name = "source_acc_id")
    private long sourceAccId; // источник платежа
    private String src_acc_num; // счет клиента с которого списались деньги
    @Column(name = "dest_acc_id")
    private long destAccId;   // получатель платежа
    private String dest_acc_num; // счет клиента на который пришли деньги
    private String reason;  // назначение платежа


    public PaymentsJournal() {
    }

    public PaymentsJournal(double amount, long sourceAccId, String src_acc_num,
                           long destAccId, String dest_acc_num, String reason) {
        this.amount = amount;
        this.sourceAccId = sourceAccId;
        this.src_acc_num = src_acc_num;
        this.destAccId = destAccId;
        this.dest_acc_num = dest_acc_num;
        this.reason = reason;
    }

    public PaymentsJournal(Accounts source, Accounts dest, NewPay newPay) {
        this.amount = newPay.getAmount();
        this.sourceAccId = source.getClientId();
        this.src_acc_num = source.getAccount_num();
        this.destAccId = dest.getClientId();
        this.dest_acc_num = dest.getAccount_num();
        this.reason = newPay.getReason();
    }

    public PaymentsJournal(NewPay newPay) {
        this.amount = newPay.getAmount();
        this.sourceAccId = newPay.getSource_acc_id();
        this.src_acc_num = null;
        this.destAccId = newPay.getDest_acc_id();
        this.dest_acc_num = null;
        this.reason = newPay.getReason();
    }

    public void setSourceAccId(long sourceAccId) { this.sourceAccId = sourceAccId; }
    public void setDest_acc_num(String dest_acc_num) { this.dest_acc_num = dest_acc_num; }
    public void setSrc_acc_num(String src_acc_num) { this.src_acc_num = src_acc_num; }
    public void setDestAccId(long destAccId) { this.destAccId = destAccId; }
    public void setPayment_id(long payment_id) { this.payment_id = payment_id; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setReason(String reason) { this.reason = reason; }
    public long getSourceAccId() { return sourceAccId; }
    public String getDest_acc_num() { return dest_acc_num; }
    public String getSrc_acc_num() { return src_acc_num; }
    public long getDestAccId() { return destAccId; }
    public String getTimestamp() { return timestamp; }
    public long getPayment_id() { return payment_id; }
    public double getAmount() { return amount; }
    public String getReason() { return reason; }
}
