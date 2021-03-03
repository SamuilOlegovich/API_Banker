package com.samuilolegovich.APIBanker.model.repo;

import com.samuilolegovich.APIBanker.model.db.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomizedAccountsRepository extends JpaRepository<Accounts, Long> {
    List<Accounts> findAllByClientId (long client_id);
}
