package com.chatbots.app.repositories;

import com.chatbots.app.models.entities.AllowedOrigins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlloweOriginsRepository extends JpaRepository<AllowedOrigins,Long> {
}
