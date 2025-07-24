package com.brokingapp.repository;

import com.brokingapp.model.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HoldingRepository extends JpaRepository<Holding, Long> {
    List<Holding> findByUserId(Long userId);
} 