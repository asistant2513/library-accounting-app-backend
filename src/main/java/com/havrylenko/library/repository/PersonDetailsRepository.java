package com.havrylenko.library.repository;

import com.havrylenko.library.model.entity.PersonDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDetailsRepository extends JpaRepository<PersonDetails, Long> {
}
