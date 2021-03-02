package com.samuilolegovich.APIBanker.model.inObjects;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Account {
    private String account_num;
    private String account_type;
    private double balance;

}
