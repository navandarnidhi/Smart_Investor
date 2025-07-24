package com.brokingapp.service;

import com.brokingapp.model.AdminAction;
import com.brokingapp.repository.AdminActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminActionRepository adminActionRepository;

    public List<AdminAction> getActionsByAdmin(Long adminId) {
        return adminActionRepository.findByAdminId(adminId);
    }

    public AdminAction recordAction(Long adminId, String actionType, Long targetUserId, String details) {
        AdminAction action = new AdminAction(null, adminId, actionType, targetUserId, details, LocalDateTime.now());
        return adminActionRepository.save(action);
    }

    // Stub methods for user management, KYC approval, reporting dashboard
    public String manageUsers() { return "User management stub"; }
    public String approveKyc(Long userId) { return "KYC approved for user " + userId; }
    public String getReportingDashboard() { return "Reporting dashboard stub"; }
} 