package com.samuilolegovich.APIBanker.model.repo;

import com.samuilolegovich.APIBanker.model.db.PaymentsJournal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentsJournalRepository extends JpaRepository<PaymentsJournal, Long> {
    List<PaymentsJournal> findAllBySourceAccIdAndDestAccId (long source_acc_id, long dest_acc_id);
}
