package com.brokingapp.service;

import com.brokingapp.model.AdminAction;
import com.brokingapp.model.User;
import com.brokingapp.model.Order;
import com.brokingapp.model.FundTransaction;
import com.brokingapp.repository.AdminActionRepository;
import com.brokingapp.repository.UserRepository;
import com.brokingapp.repository.OrderRepository;
import com.brokingapp.repository.FundTransactionRepository;
import com.brokingapp.repository.KycRepository;
import com.brokingapp.model.Kyc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class AdminService {
    @Autowired
    private AdminActionRepository adminActionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FundTransactionRepository fundTransactionRepository;
    @Autowired
    private KycRepository kycRepository;

    public List<AdminAction> getActionsByAdmin(Long adminId) {
        return adminActionRepository.findByAdminId(adminId);
    }

    public AdminAction recordAction(Long adminId, String actionType, Long targetUserId, String details) {
        AdminAction action = new AdminAction(null, adminId, actionType, targetUserId, details, LocalDateTime.now());
        return adminActionRepository.save(action);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<FundTransaction> getAllFundTransactions() {
        return fundTransactionRepository.findAll();
    }

    // Stub methods for user management, KYC approval, reporting dashboard
    public String manageUsers() { return "User management stub"; }
    public String approveKyc(Long userId) {
        Kyc kyc = kycRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("KYC not found for user"));
        kyc.setStatus("APPROVED");
        kycRepository.save(kyc);
        return "KYC approved for user " + userId;
    }

    public String rejectKyc(Long userId) {
        Kyc kyc = kycRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("KYC not found for user"));
        kyc.setStatus("REJECTED");
        kycRepository.save(kyc);
        return "KYC rejected for user " + userId;
    }
    public Map<String, Object> getReportingDashboard() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("userCount", userRepository.count());
        stats.put("orderCount", orderRepository.count());
        stats.put("totalFundsAdded", fundTransactionRepository.findAll().stream().filter(f -> f.getAmount() > 0).mapToDouble(f -> f.getAmount()).sum());
        stats.put("totalFundsWithdrawn", fundTransactionRepository.findAll().stream().filter(f -> f.getAmount() < 0).mapToDouble(f -> Math.abs(f.getAmount())).sum());
        stats.put("kycApproved", kycRepository.findAll().stream().filter(k -> "APPROVED".equalsIgnoreCase(k.getStatus())).count());
        stats.put("kycPending", kycRepository.findAll().stream().filter(k -> "PENDING".equalsIgnoreCase(k.getStatus())).count());
        stats.put("kycRejected", kycRepository.findAll().stream().filter(k -> "REJECTED".equalsIgnoreCase(k.getStatus())).count());
        return stats;
    }
} 