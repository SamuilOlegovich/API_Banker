package com.samuilolegovich.APIBanker.model.repo;

import com.samuilolegovich.APIBanker.model.db.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Clients, Long> {
}
