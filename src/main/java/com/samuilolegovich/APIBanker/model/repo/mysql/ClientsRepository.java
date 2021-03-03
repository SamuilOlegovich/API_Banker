package com.samuilolegovich.APIBanker.model.repo.mysql;

import org.springframework.data.repository.CrudRepository;
import com.samuilolegovich.APIBanker.model.db.Clients;

public interface ClientsRepository extends CrudRepository<Clients, Long> {
}
