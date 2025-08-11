package com.smartinvestor.service;

import lombok.With;

import java.util.List;

import com.smartinvestor.model.User;
import com.smartinvestor.model.Withdrawal;

public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount,User user);
    Withdrawal procedWithdrawal(Long withdrawalId,boolean accept) throws Exception;
    List<Withdrawal> getUsersWithdrawalHistory(User user);
    List<Withdrawal> getAllWithdrawalRequest();
}
