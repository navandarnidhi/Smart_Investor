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
    public String getAllUsers() {
        return adminService.manageUsers();
    }

    @PostMapping("/kyc/approve/{userId}")
    public String approveKyc(@PathVariable Long userId) {
        return adminService.approveKyc(userId);
    }

    @GetMapping("/dashboard")
    public String getReportingDashboard() {
        return adminService.getReportingDashboard();
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