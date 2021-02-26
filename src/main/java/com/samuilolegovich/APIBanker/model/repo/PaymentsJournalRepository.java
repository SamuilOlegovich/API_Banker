package com.samuilolegovich.APIBanker.model.repo;

import com.samuilolegovich.APIBanker.model.db.PaymentsJournal;
import org.springframework.data.repository.CrudRepository;

public interface PaymentsJournalRepository  extends CrudRepository<PaymentsJournal, Long> {
}
