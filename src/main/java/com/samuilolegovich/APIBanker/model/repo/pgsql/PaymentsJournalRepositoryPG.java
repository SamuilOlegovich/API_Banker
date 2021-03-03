package com.samuilolegovich.APIBanker.model.repo.pgsql;

import com.samuilolegovich.APIBanker.model.db.Clients;
import com.samuilolegovich.APIBanker.model.db.PaymentsJournal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentsJournalRepositoryPG extends JpaRepository<PaymentsJournal, Long> {
    List<PaymentsJournal> findAllBySourceAccIdAndDestAccId (long source_acc_id, long dest_acc_id);
}
