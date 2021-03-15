package com.samuilolegovich.APIBanker.model.db;


import com.samuilolegovich.APIBanker.model.inObjects.Account;

import javax.persistence.*;




@Entity
public class Accounts {

    @Id // @ID - Важно чтобы была из библиотеке -> javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "client_id")
    private long clientId;
    @Column(name = "account_num")
    private String accountNum;
    @Column(name = "account_type")
    private String accountType;
    private double balance;

    public Accounts() {
    }

    public Accounts(long clientId, String accountNum, String accountType, double balance) {
        this.clientId = clientId;
        this.accountNum = accountNum;
        this.accountType = accountType;
        this.balance = balance;
    }

    public Accounts(Clients clients, Account account) {
        this.clientId = clients.getId();
        this.accountNum = account.getAccount_num();
        this.accountType = account.getAccount_type();
        this.balance = account.getBalance();
    }

    public void setBalance(double balance) { this.balance = balance; }
    public String getAccountType() { return accountType; }
    public String getAccountNum() { return accountNum; }
    public long getClientId() { return clientId; }
    public double getBalance() { return balance; }
    public void setId(long id) { this.id = id; }
    public long getId() { return id; }
}
