package com.samuilolegovich.APIBanker.model.inObjects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;


@Data
@AllArgsConstructor
@XmlRootElement(name = "root")
@XmlType(propOrder = { "first_name", "last_name", "accounts"})
@JsonPropertyOrder({"first_name", "last_name", "accounts"})
public class NewClient {
    private String first_name;
    private String last_name;
    private ArrayList<Account> accounts;

    public NewClient() {
    }

    @XmlElement
    public String getFirst_name() {
        return first_name;
    }
    @XmlElement
    public String getLast_name() {
        return last_name;
    }
    @XmlElement
    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
