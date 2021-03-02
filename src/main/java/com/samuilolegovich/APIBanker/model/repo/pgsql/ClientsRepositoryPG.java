package com.samuilolegovich.APIBanker.model.repo.pgsql;

import com.samuilolegovich.APIBanker.model.db.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepositoryPG extends JpaRepository<Clients, Long> {
}
