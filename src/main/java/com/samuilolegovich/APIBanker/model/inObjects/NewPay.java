package com.samuilolegovich.APIBanker.model.inObjects;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@AllArgsConstructor
@XmlRootElement(name = "root")
@XmlType(propOrder = {"source_acc_id", "dest_acc_id", "amount", "reason"})
@JsonPropertyOrder({"source_acc_id", "dest_acc_id", "amount", "reason"})
public class NewPay {
    private long source_acc_id;
    private long dest_acc_id;
    private double amount;
    private String reason;  // назначение платежа

    public NewPay() {
    }

    @XmlElement
    public long getSource_acc_id() {
        return source_acc_id;
    }
    @XmlElement
    public long getDest_acc_id() {
        return dest_acc_id;
    }
    @XmlElement
    public double getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }
}
