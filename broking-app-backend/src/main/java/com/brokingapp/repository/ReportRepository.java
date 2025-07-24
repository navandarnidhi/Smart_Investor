package com.brokingapp.repository;

import com.brokingapp.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUserIdAndType(Long userId, String type);
} 