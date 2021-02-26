package com.samuilolegovich.APIBanker.model.repo;

import org.springframework.data.repository.CrudRepository;
import com.samuilolegovich.APIBanker.model.db.Accounts;

public interface AccountsRepository  extends CrudRepository<Accounts, Long> {
}
