package com.samuilolegovich.APIBanker.model.db;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.samuilolegovich.APIBanker.model.inObjects.Account;

import javax.persistence.*;

@Entity
public class Accounts {
    // класс отвечает за таблицу
    // (если такой таблицы нет то при первом запуске он ее создаст)

    @Id // @ID - Важно чтобы была из библиотеке -> javax.persistence.Id (айди для уникального индификатора в таблице)
    @GeneratedValue(strategy = GenerationType.AUTO) // позволит генирировать при каждом добавлении новый айди
    private long id;

    @Column(name = "client_id")
    private long clientId;
    private String account_num;
    private String account_type;
    private double balance;

    public Accounts() {
    }

    public Accounts(long clientId, String account_num, String account_type, double balance) {
        this.clientId = clientId;
        this.account_num = account_num;
        this.account_type = account_type;
        this.balance = balance;
    }

    public Accounts(Clients clients, Account account) {
        this.clientId = clients.getId();
        this.account_num = account.getAccount_num();
        this.account_type = account.getAccount_type();
        this.balance = account.getBalance();
    }

    public void setAccount_type(String account_type) { this.account_type = account_type; }
    public void setAccount_num(String account_num) { this.account_num = account_num; }
    public void setClientId(long clientId) { this.clientId = clientId; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getAccount_type() { return account_type; }
    public String getAccount_num() { return account_num; }
    public long getClientId() { return clientId; }
    public double getBalance() { return balance; }
    public void setId(long id) { this.id = id; }
    public long getId() { return id; }
}
