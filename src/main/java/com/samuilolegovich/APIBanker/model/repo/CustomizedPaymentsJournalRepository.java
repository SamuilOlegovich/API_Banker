package com.samuilolegovich.APIBanker.model.repo;

import com.samuilolegovich.APIBanker.model.db.PaymentsJournal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizedPaymentsJournalRepository extends CrudRepository<PaymentsJournal, Long> {
    List<PaymentsJournal> findAllBySourceAccIdAndDestAccId (long source_acc_id, long dest_acc_id);
}
