package com.samuilolegovich.APIBanker.model.repo;

import com.samuilolegovich.APIBanker.model.db.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
}
