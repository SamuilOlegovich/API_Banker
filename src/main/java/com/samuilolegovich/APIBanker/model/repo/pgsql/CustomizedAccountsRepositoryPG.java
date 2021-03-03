package com.samuilolegovich.APIBanker.model.repo.pgsql;

import com.samuilolegovich.APIBanker.model.db.Accounts;
import com.samuilolegovich.APIBanker.model.db.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomizedAccountsRepositoryPG extends JpaRepository<Accounts, Long> {
    List<Accounts> findAllByClientId (long client_id);
}
