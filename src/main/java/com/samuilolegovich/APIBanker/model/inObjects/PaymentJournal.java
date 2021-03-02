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
@XmlType(propOrder = {"payer_id", "recipient_id", "source_acc_id", "dest_acc_id"})
@JsonPropertyOrder({"payer_id", "recipient_id", "source_acc_id", "dest_acc_id"})
public class PaymentJournal {
    private long payer_id;
    private long recipient_id;
    private long source_acc_id;
    private long dest_acc_id;

    public PaymentJournal() {
    }

    @XmlElement
    public long getPayer_id() {
        return payer_id;
    }
    @XmlElement
    public long getRecipient_id() {
        return recipient_id;
    }
    @XmlElement
    public long getSource_acc_id() {
        return source_acc_id;
    }
    @XmlElement
    public long getDest_acc_id() {
        return dest_acc_id;
    }
}
