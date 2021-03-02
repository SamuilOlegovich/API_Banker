package com.samuilolegovich.APIBanker.model.inObjects;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewPay {
    private long source_acc_id;
    private long dest_acc_id;
    private double amount;
    private String reason;  // назначение платежа

}
