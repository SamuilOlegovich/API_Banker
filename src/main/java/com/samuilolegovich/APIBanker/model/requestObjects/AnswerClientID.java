package com.samuilolegovich.APIBanker.model.requestObjects;

import com.samuilolegovich.APIBanker.model.db.Accounts;



public class AnswerClientID {
    private long account_id;
    private String account_num;
    private String account_type;
    private double balance;

    public AnswerClientID() {
    }

    public AnswerClientID(int account_id, String account_num, String account_type, int balance) {
        this.account_id = account_id;
        this.account_num = account_num;
        this.account_type = account_type;
        this.balance = balance;
    }

    public AnswerClientID(Accounts accounts) {
        this.account_id = accounts.getId();
        this.account_num = accounts.getAccount_num();
        this.account_type = accounts.getAccount_type();
        this.balance = accounts.getBalance();
    }


    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount_num() {
        return account_num;
    }

    public void setAccount_num(String account_num) {
        this.account_num = account_num;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
