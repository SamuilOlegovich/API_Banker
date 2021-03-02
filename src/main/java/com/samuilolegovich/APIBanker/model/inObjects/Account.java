package com.samuilolegovich.APIBanker.model.inObjects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@Data
@AllArgsConstructor
@XmlRootElement(name = "accounts")
@XmlType(propOrder = {"account_num", "account_type", "balance"})
@JsonPropertyOrder({"account_num", "account_type", "balance"})
public class Account {
    private String account_num;
    private String account_type;
    private double balance;

    public Account() {
    }

    @XmlElement
    public String getAccount_num() {
        return account_num;
    }

    @XmlElement
    public String getAccount_type() {
        return account_type;
    }

    @XmlElement
    public double getBalance() {
        return balance;
    }
}
