package com.samuilolegovich.APIBanker.model.repo.pgsql;

import com.samuilolegovich.APIBanker.model.db.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepositoryPG extends JpaRepository<Accounts, Long> {
}
