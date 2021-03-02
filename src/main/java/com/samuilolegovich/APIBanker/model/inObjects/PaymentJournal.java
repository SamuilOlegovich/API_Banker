package com.samuilolegovich.APIBanker.model.inObjects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentJournal {
    private long payer_id;
    private long recipient_id;
    private long source_acc_id;
    private long dest_acc_id;
}
