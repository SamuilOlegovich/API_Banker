package com.samuilolegovich.APIBanker.model.inObjects;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;


@Data
@AllArgsConstructor
public class NewClient {
    private String first_name;
    private String last_name;
    private ArrayList<Account> accounts;

}
