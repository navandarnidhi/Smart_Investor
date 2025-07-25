package com.brokingapp.controller;

import com.brokingapp.model.AdminAction;
import com.brokingapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public List<com.brokingapp.model.User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PostMapping("/kyc/approve/{userId}")
    public String approveKyc(@PathVariable Long userId) {
        return adminService.approveKyc(userId);
    }

    @PostMapping("/kyc/reject/{userId}")
    public String rejectKyc(@PathVariable Long userId) {
        return adminService.rejectKyc(userId);
    }

    @GetMapping("/dashboard")
    public java.util.Map<String, Object> getReportingDashboard() {
        return adminService.getReportingDashboard();
    }

    @GetMapping("/orders")
    public List<com.brokingapp.model.Order> getAllOrders() {
        return adminService.getAllOrders();
    }

    @GetMapping("/funds")
    public List<com.brokingapp.model.FundTransaction> getAllFundTransactions() {
        return adminService.getAllFundTransactions();
    }

    @GetMapping("/actions/{adminId}")
    public List<AdminAction> getActionsByAdmin(@PathVariable Long adminId) {
        return adminService.getActionsByAdmin(adminId);
    }

    @PostMapping("/actions/record")
    public AdminAction recordAction(@RequestBody AdminAction action) {
        return adminService.recordAction(action.getAdminId(), action.getActionType(), action.getTargetUserId(), action.getDetails());
    }
} 