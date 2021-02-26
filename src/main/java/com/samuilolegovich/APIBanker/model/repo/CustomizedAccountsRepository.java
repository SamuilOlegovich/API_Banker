package com.samuilolegovich.APIBanker.model.repo;

import com.samuilolegovich.APIBanker.model.db.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomizedAccountsRepository extends CrudRepository<Accounts, Long> {
//    Optional<Accounts> findAllByClientId (long client_id);
    List<Accounts> findAllByClientId (long client_id);
}
