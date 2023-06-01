package com.havrylenko.library.repository;

import com.havrylenko.library.model.entity.ReaderCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderCardRepository extends JpaRepository<ReaderCard, String> {
}
