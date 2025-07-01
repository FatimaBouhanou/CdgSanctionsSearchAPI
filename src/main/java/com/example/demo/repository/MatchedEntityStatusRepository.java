package com.example.demo.repository;

import com.example.demo.model.MatchedEntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchedEntityStatusRepository extends JpaRepository<MatchedEntityStatus, Long> {
}
