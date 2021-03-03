package com.samuilolegovich.APIBanker.model.repo.mysql;

import com.samuilolegovich.APIBanker.model.db.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizedAccountsRepository extends CrudRepository<Accounts, Long> {
    List<Accounts> findAllByClientId (long client_id);
}
