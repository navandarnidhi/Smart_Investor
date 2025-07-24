package com.brokingapp.repository;

import com.brokingapp.model.AdminAction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdminActionRepository extends JpaRepository<AdminAction, Long> {
    List<AdminAction> findByAdminId(Long adminId);
} 