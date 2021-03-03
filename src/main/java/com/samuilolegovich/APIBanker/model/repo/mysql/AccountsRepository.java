package com.samuilolegovich.APIBanker.model.repo.mysql;

import org.springframework.data.repository.CrudRepository;
import com.samuilolegovich.APIBanker.model.db.Accounts;

public interface AccountsRepository  extends CrudRepository<Accounts, Long> {
}
